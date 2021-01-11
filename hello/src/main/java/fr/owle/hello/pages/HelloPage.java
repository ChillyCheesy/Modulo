package fr.owle.hello.pages;

import fr.owle.hello.entity.Ewok;
import fr.owle.hometracker.pages.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Index("hello")
public class HelloPage implements Page {

    @Resource
    @GetRequest("/")
    public String getInterface() {
        return "hello/dist";
    }

    @GetRequest("/ewok")
    public Ewok createEwok(@QueryParam(value = "name", defaultValue = "Wicket") String name, @QueryParam(value = "food", defaultValue = "apple") String food) {
        return new Ewok(name, food);
    }

    @GetRequest("/ewoks")
    public List<Ewok> createEwoks() {
        return new ArrayList<Ewok>(Arrays.asList(new Ewok("Wicket", "Apple"), new Ewok("Graak", "Apple"), new Ewok("Lewok", "Bites")));
    }

    @PostRequest("/ewoks")
    public Ewok postEwok(@Body Ewok ewok) {
        ewok.setName("Charle");
        return ewok;
    }

}
