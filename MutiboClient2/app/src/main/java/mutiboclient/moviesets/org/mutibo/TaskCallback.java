/* 
**
** Copyright 2014, Jules White
**
** 
*/
package mutiboclient.moviesets.org.mutibo;

public interface TaskCallback<T> {

    public void success(T result);

    public void error(Exception e);

}
