package com.example.btcpro.dolphons;

/**
 * Created by Tyler Rice on 4/11/2018.
 */

public class Upload
{
    private String name;
    private String imageUrl;

    public Upload()
    {
        //empty constructor needed
    }

    public Upload(String p_imageUrl)
    {
        imageUrl = p_imageUrl;
    }

    public String getName()
    {
        return name;
    }

    public void setName(String p_name)
    {
        name = p_name;
    }

    public String getImageUrl()
    {
        return imageUrl;
    }

    public void setImageUrl(String p_imageUrl)
    {
        imageUrl = p_imageUrl;
    }
}
