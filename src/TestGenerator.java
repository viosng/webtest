import org.openqa.selenium.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: VIO
 * Date: 20.05.13
 * Time: 18:07
 * To change this template use File | Settings | File Templates.
 */
public class TestGenerator {

    private static interface Handler {
        Tag handle(WebElement e);
    }

    private static HashMap<String, Handler> handlers;
    static {
        handlers = new HashMap<String, Handler>();
        Handler obj = new Handler() {
            @Override
            public Tag handle(WebElement e) {
                return new ObjectTag(e);
            }
        };
        Handler input = new Handler() {
            @Override
            public Tag handle(WebElement e) {
                String type = e.getAttribute("type");
                if(type == null){
                    return new InputTag(e);
                }
                if(type.equals("file")){
                    return new ObjectTag(e);
                }
                if(type.equals("button") || type.equals("submit") || type.equals("reset") || type.equals("image")){
                    return new ButtonTag(e);
                }
                if(type.equals("radio")){
                    return new RadioButtonTag(e);
                }
                if(type.equals("checkbox")){
                    return new CheckBoxTag(e);
                }
                if(type.equals("hidden")){
                    return null;
                }
                return new InputTag(e);
            }
        };
        Handler ref = new Handler() {
            @Override
            public Tag handle(WebElement e) {
                return new ReferenceTag(e);
            }
        };
        Handler button = new Handler() {
            @Override
            public Tag handle(WebElement e) {
                return new ButtonTag(e);
            }
        };
        Handler select = new Handler() {
            @Override
            public Tag handle(WebElement e) {
                return new SelectTag(e);
            }
        };
        handlers.put("audio", obj);
        handlers.put("video", obj);
        handlers.put("embed", obj);
        handlers.put("object", obj);
        handlers.put("select", select);
        handlers.put("input", input);
        handlers.put("a", ref);
        handlers.put("button", button);
    }

    private static void normalize(WebDriver driver, WebElement root) {
        ((JavascriptExecutor) driver).executeScript("arguments[0].innerHTML = arguments[0].innerHTML.replace(/<!--(?:(?:\\s|.)*?)-->/gm, '');" +
                "        var code = arguments[0].innerHTML;\n" +
                "        var tags = [\"p\", \"b\", \"tt\", \"h1\", \"h2\", \"h3\", \"h4\", \"h5\", \"h6\", \"strong\"," +
                "            \"strike\", \"s\", \"pre\", \"nobr\", \"sub\", \"sup\", \"em\", \"u\", \"font\"];\n" +
                "        for(var i = 0; i < tags.length; i++) {\n" +
                "            var tag = tags[i];\n" +
                "            code = code.replace(new RegExp(\"<\" + tag + \"(?:\\s|.)*?>((?:\\s|.)*?)</\" + tag + \">\", \"gmi\"), \"$1\");\n" +
                "        }\n" +
                "        arguments[0].innerHTML = code;\n" +
                "        var q = new Array()\n" +
                "        q[0] = arguments[0];\n" +
                "        var index = 0;\n" +
                "        var length = 1;\n" +
                "        var child, newChildHTML, n;\n" +
                "        while(index < length){\n" +
                "            node = q[index++];\n" +
                "            for(var i=0; i < node.childNodes.length; i++) {\n" +
                "                child = node.childNodes[i];\n" +
                "                if(child.childElementCount > 0){\n" +
                "                    q[length++] = child;\n" +
                "                } else if(child.textContent != \"\"){\n" +
                "                    if(child.innerHTML == null){\n" +
                "                        n = document.createElement(\"i\");\n" +
                "                        n.textContent = child.textContent;\n" +
                "                        node.replaceChild(n, child)\n" +
                "                    } else {\n" +
                "                        var name = child.tagName.toLowerCase();" +
                "                        if(name != 'li' && name != 'a') child.innerHTML = \"<i>\" + child.textContent + \"</i>\";\n" +
                "                    }\n" +
                "                }\n" +
                "            }\n" +
                "        }", root);
    }

    private static List<WebElement> getNotFormOrFrameElements(WebDriver driver, WebElement root) {
        return (List)((JavascriptExecutor) driver).executeScript("var q = new Array()\n" +
                "        var res = new Array();\n" +
                "        q[0] = arguments[0];\n" +
                "        var index = 0;\n" +
                "        var length = 1;\n" +
                "        var child, newChildHTML, node, name, exit;\n" +
                "        var tags = new Array(\"button\", \"a\", \"embed\", \"object\", \"input\", \"select\", \"video\", \"audio\");\n" +
                "        while(index < length){\n" +
                "            node = q[index++];\n" +
                "            for(var i = 0; i < node.childNodes.length; i++) {\n" +
                "                child = node.childNodes[i];\n" +
                "                name = child.tagName.toLowerCase();\n" +
                "                exit = false;\n" +
                "                if(name == \"iframe\" || name == \"form\"|| name == \"frame\") continue;\n" +
                "                for (var j = 0; j < tags.length; j++) {\n" +
                "                    if (tags[j].match(name)) {\n" +
                "                        res.push(child);\n" +
                "                        exit = true;\n" +
                "                        break;\n" +
                "                    }\n" +
                "                }\n" +
                "                if(!exit && child.childElementCount > 0){\n" +
                "                    q[length++] = child;\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "        return res;", root);
    }

    private static List<WebElement> getHighLeveliFrames(WebDriver driver, WebElement root) {
        return (List)((JavascriptExecutor) driver).executeScript("var q = new Array()\n" +
                "        var res = new Array();\n" +
                "        q[0] = arguments[0];\n" +
                "        var index = 0;\n" +
                "        var length = 1;\n" +
                "        var child, newChildHTML, node;\n" +
                "        while(index < length){\n" +
                "            node = q[index++];\n" +
                "            for(var i = 0; i < node.childNodes.length; i++) {\n" +
                "                child = node.childNodes[i];\n" +
                "                if(child.tagName.toLowerCase() == \"iframe\") res.push(child);\n" +
                "                else if(child.childElementCount > 0){\n" +
                "                    q[length++] = child;\n" +
                "                }\n" +
                "            }\n" +
                "        }\n" +
                "        return res;", root);
    }

    private ArrayList<Tag> tags;
    private WebDriver driver;
    private ArrayList<String> testTags, tagsWithText;
    public TestGenerator(WebDriver driver, String tagsWithTextFile, String testTagsFile) throws FileNotFoundException {
        testTags = new ArrayList<String>();
        this.driver = driver;
        tagsWithText = new ArrayList<String>();
        Scanner in = new Scanner(new File(tagsWithTextFile));
        while(in.hasNextLine()){
            tagsWithText.add(in.nextLine());
        }
        in.close();
        in = new Scanner(new File(testTagsFile));
        while(in.hasNextLine()){
            testTags.add(in.nextLine());
        }
        in.close();
    }

    public void testElement(WebElement root){
        ArrayList<String> names = new ArrayList<String>();
        ArrayList<Integer> x = new ArrayList<Integer>();
        ArrayList<Integer> y = new ArrayList<Integer>();
        String text;
        Point p;
        for(String tag : tagsWithText){
            List<WebElement> elements =  root.findElements(By.tagName(tag));
            for(WebElement e : elements){
                if(!e.isDisplayed() || !e.isEnabled()) continue;
                text = e.getText();
                if(text == null || text.replaceAll("\\s", "").equals("")) continue;
                names.add(text);
                p = e.getLocation();
                x.add(p.x);
                y.add(p.y);
            }
        }

        this.tags = new ArrayList<Tag>();
        for(String tag : testTags){
            List<WebElement> elements =  root.findElements(By.tagName(tag));
            Handler handler = handlers.get(tag);
            for(WebElement e : elements){
                if(!e.isDisplayed() || !e.isEnabled()) continue;
                Tag t = handler.handle(e);
                if(t != null){
                    tags.add(t);
                }
            }
        }
        int tagsWithTextCount = names.size();
        for(Tag tag : tags){
            for(int i = 0; i < tagsWithTextCount; i++){
                tag.updateDistAndName(names.get(i), x.get(i), y.get(i));
            }
        }

        Collections.sort(tags);
        for(Tag tag : tags){
            String s = tag.toString();
            if(!s.equals(""))
                System.out.println(tag);
        }
    }

    public void testFrame(WebDriver rootDriver) {
        if (!rootDriver.findElements(By.tagName("frameset")).isEmpty()) {
            for (WebElement frame : rootDriver.findElements(By.tagName("frame"))) {
                System.out.println("------------------Test Nested Frame----------------");
                testFrame(rootDriver.switchTo().frame(frame));
                System.out.println("---------------------------------------------------");
                rootDriver.switchTo().defaultContent();
            }
        } else {
            WebElement body = rootDriver.findElement(By.tagName("body"));
            normalize(rootDriver, body);
            for (WebElement iframe : getHighLeveliFrames(rootDriver, body)) {
                System.out.println("------------------Test Nested iFrame----------------");
                testFrame(rootDriver.switchTo().frame(iframe));
                System.out.println("---------------------------------------------------");
            }
            List<WebElement> other = getNotFormOrFrameElements(rootDriver, rootDriver.findElement(By.tagName("body")));
            List<WebElement> forms = rootDriver.findElements(By.tagName("form"));
            for (WebElement root : forms) {
                System.out.println("---------------------- Test form -----------------------");
                testElement(root);
                System.out.println("-------------------------------------------------------");
            }
            ArrayList<String> names = new ArrayList<String>();
            ArrayList<Integer> x = new ArrayList<Integer>();
            ArrayList<Integer> y = new ArrayList<Integer>();
            String text;
            Point p;
            for (String tag : tagsWithText) {
                List<WebElement> elements = driver.findElements(By.tagName(tag));
                for (WebElement e : elements) {
                    if (!e.isDisplayed() || !e.isEnabled()) continue;
                    text = e.getText();
                    if (text == null || text.replaceAll("\\s", "").equals("")) continue;
                    names.add(text);
                    p = e.getLocation();
                    x.add(p.x);
                    y.add(p.y);
                }
            }

            this.tags = new ArrayList<Tag>();
        /*for(String tag : testTags){
            List<WebElement> elements =  driver.findElements(By.tagName(tag));
            Handler handler = handlers.get(tag);
            for(WebElement e : elements){
                if(!e.isDisplayed() || !e.isEnabled()) continue;
                Tag t = handler.handle(e);
                if(t != null){
                    tags.add(t);
                }
            }
        }*/
            for (WebElement e : other) {
                String tag = e.getTagName();
                if (!handlers.containsKey(tag)) continue;
                if (!e.isDisplayed() || !e.isEnabled()) continue;
                Tag t = handlers.get(tag).handle(e);
                if (t != null) {
                    tags.add(t);
                }
            }
            int tagsWithTextCount = names.size();
            for (Tag tag : tags) {
                for (int i = 0; i < tagsWithTextCount; i++) {
                    tag.updateDistAndName(names.get(i), x.get(i), y.get(i));
                }
            }
            Collections.sort(tags);
            System.out.println("----------------Other Elements---------------------");
            for (Tag tag : tags) {
                String s = tag.toString();
                if (!s.equals(""))
                    System.out.println(tag);
            }
        }
        rootDriver.switchTo().defaultContent();
    }

    public void test() {
        testFrame(driver.switchTo().defaultContent());
    }

}
