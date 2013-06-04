import org.openqa.selenium.WebElement;

/**
 * Created with IntelliJ IDEA.
 * User: VIO
 * Date: 19.05.13
 * Time: 10:50
 * To change this template use File | Settings | File Templates.
 */
public class ReferenceTag extends Tag {

    public ReferenceTag(WebElement e) {
        super(e);
        d = 0;
        if(e.getText() != null) name = e.getText();
    }

    @Override
    public void updateDistAndName(String name, int x, int y) {
        return;
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
        if(name.equals("")) return "";
        StringBuffer buffer = new StringBuffer();
        buffer.append("Перейти по ссылке с именем \"");
        buffer.append(name);
        buffer.append("\".");
        return buffer.toString();
    }
}
