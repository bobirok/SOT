package shared.model;

import java.util.Objects;

/**
 * This class is used to put two objects (class MyRequest and class MyReply) in one JList
 * line: ListLine<MyRequest,MyReply> listLine;
 *
 * It makes sure that requests and replies are shown in a nice way (see the toString method):
 *
 *  How old is Maja? ---> waiting for reply...
 *  How old is Joe? ---> 50
 *  How old is Bert? ---> waiting for reply...
 *  How old is Mike? ---> 65
 *
 *  Do NOT put Messages here: ListLine<Message,Message> listLine; messages should not be in the GUI!
 * @author 884294
 */
public class ListLine<REQUEST,REPLY> {

    private final REQUEST request;
    private REPLY reply;

    public ListLine(REQUEST request, REPLY reply) {
        this.request = request;
        this.reply = reply;
    }

    public ListLine(REQUEST request) {
        this.request = request;
        this.reply = null;
    }


    public REQUEST getRequest() {
        return request;
    }

    public void setReply(REPLY reply) {
        this.reply = reply;
    }

    @Override
    public String toString() {
        String requestString, replyString;
        if (request == null){
            requestString = "can't find request...";
        } else {
            requestString = request.toString();
        }

        if (reply == null){
            replyString = "can't find reply...";
        } else {
            replyString = reply.toString();
        }
        return requestString+ "  --->  " + replyString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ListLine<?, ?> listLine = (ListLine<?, ?>) o;
        return request.equals(listLine.request) &&
                Objects.equals(reply, listLine.reply);
    }

    @Override
    public int hashCode() {
        return Objects.hash(request, reply);
    }
}
