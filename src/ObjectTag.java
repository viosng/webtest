import org.openqa.selenium.WebElement;

/**
 * Created with IntelliJ IDEA.
 * User: VIO
 * Date: 20.05.13
 * Time: 17:48
 * To change this template use File | Settings | File Templates.
 */
public class ObjectTag extends Tag{

    public ObjectTag(WebElement e){
        super(e);
    }

    @Override
    public void updateDistAndName(String name, int x, int y) {
        if(x > p.x) return;
        super.updateDistAndName(name, x, y);
    }

    @Override
    public String getValue() {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setValue(String value) {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public String toString() {
        StringBuffer buffer = new StringBuffer();
        String tag = element.getTagName();
        if(tag.equals("input")) buffer.append("Загрузить файл в \"");
        else if(tag.equals("embed") || tag.equals("object")) buffer.append("Потестировать \"");
        else if(tag.equals("video")) buffer.append("Посмотреть видео \"");
        else buffer.append("Послушать \"");
        buffer.append(name);
        buffer.append("\".");
        return buffer.toString();
    }
}
