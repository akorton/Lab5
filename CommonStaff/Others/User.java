package Lab5.CommonStaff.Others;

import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.Objects;
import java.util.Random;

public class User implements Serializable {
    private final String name;
    private byte[] encodedPassword;
    private String password;
    private String salt;
    private static String pepper = "!ajsld826?>{";

    public User(String name, String password) throws PasswordGenerationException {
        this.name = name;
        this.salt = getRandomString();
        this.password = password;
        this.encodedPassword = encodePassword(password);
    }

    public User(String name, byte[] encodedPassword, String salt){
        this.name = name;
        this.salt = salt;
        this.encodedPassword = encodedPassword;
    }

    public User(String name, String password, String salt) throws PasswordGenerationException {
        this.name = name;
        this.salt = salt;
        this.password = password;
        this.encodedPassword = new String(encodePassword(password)).getBytes(StandardCharsets.UTF_8);
    }

    public byte[] getEncodedPassword() {
        return encodedPassword;
    }

    public String getName() {
        return name;
    }

    private String arrToString(byte[] arr){
        StringBuilder ans = new StringBuilder();
        for (byte b: arr){
            ans.append(b);
        }
        return ans.toString();
    }

    public String getSalt() {
        return salt;
    }

    private byte[] encodePassword(String password) throws PasswordGenerationException {
        try {
            MessageDigest sha_512 = MessageDigest.getInstance("SHA-512");
            return sha_512.digest((pepper + password + salt).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e){
            throw new PasswordGenerationException();
        }
    }

    public void encodePassword(){
        try {
            this.encodedPassword = encodePassword(password);
        } catch (PasswordGenerationException e) {

        }
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    private String getRandomString(){
        return getRandomString(8);
    }

    private String getRandomString(int length){
        Random random = new Random();
        StringBuilder ans = new StringBuilder();
        for (int i = 0; i < length;i++){
            ans.append((char) 97 + random.nextInt(26));
        }
        return ans.toString();
    }

    public String toString(){
        return name+"\n"+arrToString(encodedPassword);
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
