package shared.model;

import java.io.*;
import java.util.Base64;
import java.util.Objects;

/**
 * @TODO
 *  Refactor this class to give it a meaningful name.
 *  Add  fields, constructors and methods which you want.
 */
public class MyRequest implements Serializable {
    private String text;

    public MyRequest() {

    }

    public MyRequest(String text) {
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MyRequest myRequest = (MyRequest) o;
        return Objects.equals(text, myRequest.text);
    }

    @Override
    public int hashCode() {
        return Objects.hash(text);
    }

    @Override
    public String toString() {
        return "MyRequest{" +
                "text='" + text + '\'' +
                '}';
    }

    public String getSerialized() {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();

            ObjectOutputStream oos = new ObjectOutputStream(baos);

            oos.writeObject(this);

            oos.close();

            return Base64.getEncoder().encodeToString(baos.toByteArray());
        } catch(IOException e) {
            System.out.println(e.getMessage());
        }
        return "";
    }

    public static MyRequest getDeserialized(String s) {
        try {
            byte[] data = Base64.getDecoder().decode(s);

            ObjectInputStream ois = new ObjectInputStream(

                    new ByteArrayInputStream(data));

            Object o = ois.readObject();

            ois.close();

            return (MyRequest)o;
        } catch(IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }
}

