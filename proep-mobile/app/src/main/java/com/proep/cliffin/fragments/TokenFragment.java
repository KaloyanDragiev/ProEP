package com.proep.cliffin.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;

import com.proep.cliffin.R;

;
//import android.support.v4.app.Fragment;


/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link TokenFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link TokenFragment#newInstance} factory method to
 * create an instance of this fragment.
 *
 */
public class TokenFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private static boolean authComplete = false;
    private static String authCode;

    /**
     *
     * @return fragment
     */
    public static TokenFragment newInstance() {
        TokenFragment fragment = new TokenFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    public TokenFragment() {
        // Required empty public constructor
    }
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);



    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        //android.webkit.CookieManager.getInstance().removeAllCookie();

        FrameLayout ll = (FrameLayout) inflater.inflate(R.layout.fragment_token, container, false);

        WebView web = (WebView) ll.findViewById(R.id.webv_fr);
        web.getSettings().setJavaScriptEnabled(true);

        web.setWebViewClient(new WebViewClient() {

            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon){
                super.onPageStarted(view, url, favicon);
                if (authComplete){
                    closeFragment();
                }
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

                if (url.contains("access_token=") && !authComplete) {
                    authCode = url.split("#access_token=", 2)[1].split("&", 2)[0];
                    authComplete = true;
                    closeFragment();
                } else if(url.contains("error=access_denied")){
                    Log.i("", "ACCESS_DENIED_HERE");
                    authComplete = true;
                    closeFragment();
                }
            }
        });

        String CLIENT_ID = "i874073-no-production";
        String REDIRECT_URI="https://tas.fhict.nl/oob.html";
        String OAUTH_URL ="https://identity.fhict.nl/connect/authorize";
        String OAUTH_SCOPE="fhict fhict_personal";
        web.loadUrl(OAUTH_URL+"?redirect_uri="+REDIRECT_URI+"&response_type=token&client_id="+CLIENT_ID+"&scope="+OAUTH_SCOPE);

        return ll;
    }

    private void closeFragment() {
        View v = getView();
        if(v != null){
            v.setVisibility(View.GONE);
        }
        if(mListener != null){
            mListener.onFragmentInteraction(authCode);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(String token);
    }
}
