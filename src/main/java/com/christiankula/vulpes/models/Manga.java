package com.christiankula.vulpes.models;

import com.christiankula.vulpes.utils.StringUtils;

import java.util.Set;

/**
 * @author Christian Kula
 * @date 22/07/2016.
 */
public class Manga {
    protected String name;

    protected String url;

    protected Set<Volume> volumes;

    public Manga(String mangaName) {
        this.name = mangaName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Set<Volume> getVolumes() {
        return volumes;
    }

    public void setVolumes(Set<Volume> volumes) {
        this.volumes = volumes;
    }
}
