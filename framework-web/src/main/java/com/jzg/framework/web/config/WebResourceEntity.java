package com.jzg.framework.web.config;

/**
 * @description:Web资源配置
 * @author: JZG
 * @date: 2016/12/1 12:04
 */
public class WebResourceEntity {
    /**
     * Getter for property 'globalCssBasePath'.
     *
     * @return Value for property 'globalCssBasePath'.
     */
    public String getGlobalCssBasePath() {
        return globalCssBasePath;
    }

    /**
     * Setter for property 'globalCssBasePath'.
     *
     * @param globalCssBasePath Value to set for property 'globalCssBasePath'.
     */
    public void setGlobalCssBasePath(String globalCssBasePath) {
        this.globalCssBasePath = globalCssBasePath;
    }

    /**
     * Getter for property 'globalJsBasePath'.
     *
     * @return Value for property 'globalJsBasePath'.
     */
    public String getGlobalJsBasePath() {
        return globalJsBasePath;
    }

    /**
     * Setter for property 'globalJsBasePath'.
     *
     * @param globalJsBasePath Value to set for property 'globalJsBasePath'.
     */
    public void setGlobalJsBasePath(String globalJsBasePath) {
        this.globalJsBasePath = globalJsBasePath;
    }

    /**
     * Getter for property 'globalImgBasePath'.
     *
     * @return Value for property 'globalImgBasePath'.
     */
    public String getGlobalImgBasePath() {
        return globalImgBasePath;
    }

    /**
     * Setter for property 'globalImgBasePath'.
     *
     * @param globalImgBasePath Value to set for property 'globalImgBasePath'.
     */
    public void setGlobalImgBasePath(String globalImgBasePath) {
        this.globalImgBasePath = globalImgBasePath;
    }

    /**
     * Getter for property 'cssBasePath'.
     *
     * @return Value for property 'cssBasePath'.
     */
    public String getCssBasePath() {
        return cssBasePath;
    }

    /**
     * Setter for property 'cssBasePath'.
     *
     * @param cssBasePath Value to set for property 'cssBasePath'.
     */
    public void setCssBasePath(String cssBasePath) {
        this.cssBasePath = cssBasePath;
    }

    /**
     * Getter for property 'jsBasePath'.
     *
     * @return Value for property 'jsBasePath'.
     */
    public String getJsBasePath() {
        return jsBasePath;
    }

    /**
     * Setter for property 'jsBasePath'.
     *
     * @param jsBasePath Value to set for property 'jsBasePath'.
     */
    public void setJsBasePath(String jsBasePath) {
        this.jsBasePath = jsBasePath;
    }

    /**
     * Getter for property 'imgBasePath'.
     *
     * @return Value for property 'imgBasePath'.
     */
    public String getImgBasePath() {
        return imgBasePath;
    }

    /**
     * Setter for property 'imgBasePath'.
     *
     * @param imgBasePath Value to set for property 'imgBasePath'.
     */
    public void setImgBasePath(String imgBasePath) {
        this.imgBasePath = imgBasePath;
    }

    /**
     * Getter for property 'version'.
     *
     * @return Value for property 'version'.
     */
    public String getVersion() {
        return version;
    }

    /**
     * Setter for property 'version'.
     *
     * @param version Value to set for property 'version'.
     */
    public void setVersion(String version) {
        this.version = version;
    }

    /**
     * 公用Css路径；例如：http://res.jingzhengu.com/ptvm/css
     */
    private String globalCssBasePath;

    /**
     * 公共JS路径；例如：http://res.jingzhengu.com/ptvm/js
     */
    private String globalJsBasePath;

    /**
     * 公用Img路径；例如：http://res.jingzhengu.com/ptvm/img
     */
    private String globalImgBasePath;

    /**
     * 项目私有css路径；例如"/static/css"
     */
    private String cssBasePath;

    /**
     * 项目私有css路径；例如"/static/js"
     */
    private String jsBasePath;

    /**
     * 项目私有css路径；例如"/static/img"
     */
    private String imgBasePath;

    /**
     * 版本号，防止CSS、JS等静态文件缓存
     */
    private String version;
}
