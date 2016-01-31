/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package bropals.lib.simplegame.leveldesigner;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Jonathon
 */
public abstract class AbstractEditableLevel implements EditableLevel {

    private ArrayList<EditableEntity> entities = new ArrayList<>();
    
    @Override
    public void addEditable(EditableEntity editable) {
        entities.add(editable);
    }

    @Override
    public void removeEditable(EditableEntity editable) {
        entities.remove(editable);
    }

    @Override
    public List<EditableEntity> getEditables() {
        return entities;
    }

    @Override
    public void clearLevel() {
        entities.clear();
    }
}
