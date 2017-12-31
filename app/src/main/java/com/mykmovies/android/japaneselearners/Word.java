package com.mykmovies.android.japaneselearners;

/**
 * Created by yeswa on 12-09-2017.
 */

public class Word {
    //**@param englishTranslation, japanese Translation,image_translation variable declaraion
   private String englishTranslation;
   private String japaneseTranslation;
    private int image_translation=NO_IMAGE_SETUP;
    private static final int NO_IMAGE_SETUP=-1;
    /**mAudioResourceId  is resourceid for word*/
    private int mAudioResourceId;
    /*
    **@PARAM audioResourceId is parameter for audio resource of each word
     */

    public Word(String vJapaneseTranslation, String vEnglishTranslation, int audioResourceId)
    {
        englishTranslation=vEnglishTranslation;
        japaneseTranslation=vJapaneseTranslation;
        mAudioResourceId=audioResourceId;

    }
    //creating construction and assign call reference to local variables
    public Word(String vJapaneseTranslation, String vEnglishTranslation, int v_image_translation, int audioResourceId)
    {
        englishTranslation=vEnglishTranslation;
        japaneseTranslation=vJapaneseTranslation;
        image_translation=v_image_translation;
        mAudioResourceId=audioResourceId;
    }
    public String getJapaneseTranslation()
    {
        return japaneseTranslation;
    }
    public String getEnglishTranslation()
    {
        return englishTranslation;
    }
    public int getImage_translation()
    {
        return image_translation;
    }
    public boolean hasImage()
    {
        return image_translation!=NO_IMAGE_SETUP;
    }
    public int getmAudioResourceId()
    {
        return mAudioResourceId;
    }
}
