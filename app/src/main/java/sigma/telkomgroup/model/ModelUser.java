package sigma.telkomgroup.model;

/**
 * Created by biting on 07/03/16.
 */
public class ModelUser {
    private String username;
    private String password;
    private String success;
    private String status;
    private String idDevice;
    private String version_apk;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSuccess() {
        return success;
    }

    public void setSuccess(String success) {
        this.success = success;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getIdDevice() {
        return idDevice;
    }

    public void setIdDevice(String idDevice) {
        this.idDevice = idDevice;
    }

    public String getVersion_apk() {
        return version_apk;
    }

    public void setVersion_apk(String version_apk) {
        this.version_apk = version_apk;
    }
}
