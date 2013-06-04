import org.openqa.selenium.WebElement;

/**
 * Created with IntelliJ IDEA.
 * User: VIO
 * Date: 19.05.13
 * Time: 10:46
 * To change this template use File | Settings | File Templates.
 */
public class CheckBoxTag extends Tag {

    public CheckBoxTag(WebElement e) {
        super(e);
    }

    @Override
    public void updateDistAndName(String name, int x, int y) {
        if(x < p.x + element.getSize().getWidth()) return;
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
        buffer.append("Отметить галочкой пункт \"");
        buffer.append(name);
        buffer.append("\".");
        return buffer.toString();
    }
}
