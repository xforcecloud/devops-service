package io.choerodon.devops.app.eventhandler.payload;

/**
 * Creator: ChangpingShi0213@gmail.com
 * Date:  11:16 2019/8/2
 * Description:
 */
public class AppServiceVersionDownloadVO {
    private String version;
    private String image;
    private String repository;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getRepository() {
        return repository;
    }

    public void setRepository(String repository) {
        this.repository = repository;
    }
}