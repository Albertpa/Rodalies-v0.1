package com.example.rodalies;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Created by jesus on 08/02/14.
 */
public class ConfiguradorTwitter {
    private static ConfiguradorTwitter   _instance;
    private Twitter twitter;

    private ConfiguradorTwitter()
    {
        ConfigurationBuilder cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true)
                .setOAuthConsumerKey("sucgKFbEBFcK7CQ2owEHXw")
                .setOAuthConsumerSecret("G2hiP52HjmlTa2VkcDfMRFeXok1EUdHYqvFpYSFc")
                .setOAuthAccessToken("58214096-4zwXE7v5PaSCgiDtkqFbl1vhJYpv2ZBUqMmCACKGc")
                .setOAuthAccessTokenSecret("EZCucXVtARGyvdipOEO4LhEVDTLD3y3upR4TUSh0zMdxR");

        TwitterFactory tf = new TwitterFactory(cb.build());
        this.twitter = tf.getInstance();
    }

    public static ConfiguradorTwitter getInstance()
    {
        if (_instance == null)
        {
            _instance = new ConfiguradorTwitter();
        }
        return _instance;
    }

    public Twitter getTwitter() {
        return twitter;
    }
}
