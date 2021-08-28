import Vue from 'vue';
import Vuex from 'vuex';
import axios from 'axios'
import VueAxios from 'vue-axios'
import {
    JumpingSide
} from '../interfaces/jumpingSite';
import {
    Article
} from '../interfaces/Article';
import {
    Event
} from '../interfaces/events';
import {
    Marker
} from '../interfaces/Marker';



Vue.use(Vuex);
Vue.use(VueAxios, axios)
//Vue.axios.defaults.baseURL = "http://cliffing.kn01.fhict.nl/";
Vue.axios.defaults.baseURL = "http://cliffing.kn01.fhict.nl/";

export const store = new Vuex.Store({
    state: {
        jumpingSides: [],
        news: [],
        events: [],
        users: [],
        token: null,
        filteredJumpingSited: [],
        currentUser: null,
        Longitude: 11,
        Latitude: 22,
        markers: [],
        dialogJumpingSite: false,
        dialogUpdateJumpingSite: false,
        dialogCreateEvent: false,
        snackbarHome: false,
        dialogCreateArticle: false,
        currentSite: null
    },

    mutations: {
        logout(state) {
            state.currentUser = null;
        },
        currentSite(state, payload) {
            state.currentSite = payload;
        },
        closeDialog(state, payload) {
            state.dialogJumpingSite = payload;
        },
        closeDialogUpdateJumpinSite(state, payload) {
            state.dialogUpdateJumpingSite = payload;
        },
        closeDialogCreateEvent(state, payload) {
            state.dialogCreateEvent = payload;
        },
        closeDialogCreateArticle(state, payload) {
            state.dialogCreateArticle = payload;
        },
        assignUser(state, payload) {
            state.currentUser[0] = payload;
        },
        increment(state) {
            state.count++;
        },
        login(state, payload) {
            state.token = payload;
        },
        addJumpingSites(state, payload) {
            state.jumpingSides = payload;
        },
        addNews(state, payload) {
            state.news = payload;
        },
        addEvents(state, payload) {
            state.events = payload;
        },
        changeCurrentUser(state, payload) {
            state.currentUser = payload;
        },
        setLongitude(state, payload) {
            state.Longitude = payload;
        },
        setLatitude(state, payload) {
            state.Latitude = payload;
        },
        setMarkers(state, payload) {
            state.markers = payload;
        }
    },
    computed: {
        filteredPhotoFeed: function () {

            let jumpingSites = this.jumpingSides;
            let jumpingSiteSearch = this.jumpingSiteSearch;

            if (!jumpingSiteSearch) {
                return jumpingSites;
            }

            jumpingSiteSearch = jumpingSiteSearch.trim().toLowerCase();

            jumpingSites = jumpingSites.filter(function (item) {
                if (item.name.toLowerCase().indexOf(jumpingSiteSearch) !== -1) {
                    return item;
                }
            });

            return jumpingSites;
        }
    },
    getters: {
        getCurrentSite: state => {
            return state.currentSite
        },
        getListOfJumpingSites: state => {
            return state.jumpingSides
        },
        name: (state) => {
            return state.name;
        },
        getNews: state => {
            return state.news
        },
        getSingleNews: (state, id) => {
            return state.news.filter(el => {
                el.id === id
            })
        },
        getCount: state => {
            return state.count
        },
        getUsers: state => {
            return state.users
        },
        getToken: state => {
            console.log(state.token);
            return state.token
        },
        getEvents: state => {
            return state.events
        },
        getCurrentUser: state => {
            return state.currentUser
        },
        getLongitude: state => {
            return state.Longitude
        },
        getLatitude: state => {
            return state.Latitude
        },
        getMarkers: state => {
            return state.markers
        },
        getdialogJumpingSiteState: state => {
            return state.dialogJumpingSite
        },
        getCurrentUserInitials: state => {
            if (state.currentUser) {
                return state.currentUser.firstName.charAt(0) + " " + state.currentUser.lastName.charAt(0);
            } else {
                return null
            }
        }
    },
    actions: {
        login(context, payload) {
            axios.post('login', {
                email: payload.email,
                password: payload.password
            }).then(result => {
                console.log(result);
                //context.commit('login', result.data);
            }).catch(error => {
                throw new Error(`API ${error}`);

            });
            //--------------------------------

        },
        register(context, payload) {
            axios.post('register/web', {
                email: payload.email,
                firstName: payload.firstName,
                lastName: payload.lastName,
                password: payload.password,
                country: payload.country,
                city: payload.city,
                experienceLevel: payload.experienceLevel
            }).then(elemet => {
                context.commit('assignUser', elemet.data);
                return elemet.data;
            });
        },
        setLongLat(context, payload) {
            context.commit('setLongitude', payload.long)
            context.commit('setLatitude', payload.lat)


        },
        getJumpingSites(context) {
            let jumpingSiteArray = [];
            axios.get('jumpingsites').then(result => {
                result.data.map(element => {
                    jumpingSiteArray.push(new JumpingSide(element.idJumpingSite, element.name, element.difficultyLevel, element.height, element.coordinates, element.rate, element.image, element.User_idUser));
                })
                context.commit('addJumpingSites', jumpingSiteArray);
                console.log(result);
            }).catch(error => {
                throw new Error(`API ${error}`);
            });
            //--------------------------------

        },
        getNews(context) {
            let NewsArray = [];
            axios.get('news').then(result => {
                result.data.map(element => {
                    NewsArray.push(new Article(element.newsId, element.title, element.body, element.timePublished, element.userId, element.image))
                })
                context.commit('addNews', NewsArray);
            }).catch(error => {
                throw new Error(`API ${error}`);
            });
        },
        getEvents(context) {
            let EventsArray = [];
            axios.get('events').then(result => {
                result.data.map(element => {
                    EventsArray.push(new Event(element.idEvent, element.name, element.date, element.description, element.JumpingSite_idJumpingSite, element.image, element.idUser))
                })
                context.commit('addEvents', EventsArray);
                console.log(result);
            }).catch(error => {
                throw new Error(`API ${error}`);
            });
        },
        AddMarkers(context) {
            let Mark = [];
            axios.get('jumpingsites').then(result => {
                result.data.map(element => {
                    if (/\s/.test(element.coordinates)) {
                        // It has any kind of whitespace

                        const splitString = element.coordinates.split(" ");
                        var lng = splitString[0];
                        var lat = splitString[1];
                        Mark.push(new Marker(lat, lng, element.name, element.difficultyLevel, element.height, element.rate));
                    }
                })
                context.commit('setMarkers', Mark);
                console.log(result);
            }).catch(error => {
                throw new Error(`API ${error}`);
            });
            //--------------------------------

        },
        AddMarkersEvents() {
            // let Mark = [];
            axios.get(`events/getSites`).then(result => {
                // result.data.map(element => {
                //     if (/\s/.test(element.coordinates)) {
                //         // It has any kind of whitespace

                //         const splitString = element.coordinates.split(" ");
                //         var lng = splitString[0];
                //         var lat = splitString[1];
                //         Mark.push(new Marker(lat, lng, element.name, element.difficultyLevel, element.height, element.rate));
                //     }
                // })
                // context.commit('setMarkers', Mark);
                console.log(result);
            }).catch(error => {
                throw new Error(`API ${error}`);
            });
            //--------------------------------

        },
        updateSite(context, site) {
            let vm = this;

            let array = [];
            vm.state.jumpingSides.map((el) => {

                if (el.id === site.id) {
                    array.push(site);
                } else {
                    array.push(el);
                }

            });
            context.commit('addJumpingSites', array);
        }


    },
});