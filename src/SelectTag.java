import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: VIO
 * Date: 19.05.13
 * Time: 10:57
 * To change this template use File | Settings | File Templates.
 */
public class SelectTag extends Tag {

    public SelectTag(WebElement e) {
        super(e);
        List<WebElement> options = element.findElements(By.tagName("option"));
        values = new ArrayList<String>();
        for(WebElement opt : options){
            if(opt.getText() != null){
                values.add(opt.getText());
            }
        }
    }

    @Override
    public void updateDistAndName(String name, int x, int y) {
        if(x > p.x || y < p.y || y > p.y + element.getSize().getHeight()) return;
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
        buffer.append("В списке \"");
        buffer.append(name);
        buffer.append("\" выбрать \"");
        //buffer.append(values.get(curValue));
        buffer.append(values);
        buffer.append("\".");
        return buffer.toString();
    }
}
