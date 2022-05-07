package Lab5.CommonStaff.Others;

import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

public class User implements Serializable {
    private final String name;
    private final byte[] encodedPassword;

    public User(String name, byte[] password){
        this.name = name;
        this.encodedPassword = password;
    }

    public byte[] getEncodedPassword() {
        return encodedPassword;
    }

    public String getName() {
        return name;
    }


    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(name, user.name) && Arrays.equals(encodedPassword, user.encodedPassword);
    }


    public int hashCode() {
        int result = Objects.hash(name);
        result = 31 * result + Arrays.hashCode(encodedPassword);
        return result;
    }
}
