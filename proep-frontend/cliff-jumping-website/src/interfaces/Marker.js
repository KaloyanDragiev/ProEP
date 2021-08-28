export class Marker {

    Longitude = 0;
    Latitude=0;
    Name='';
    difficultyLevel = "";
    height = null;
    rate = "";
   constructor(lat,long,nam,diff,heigh,rat){
       this.Latitude=lat;
       this.Longitude=long;
       this.Name=nam;
       this.difficultyLevel=diff;
       this.height=heigh;
       this.rate=rat;
   }
    
}