/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16;

import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.sound.SoundEffect;

/**
 *
 * @author Jonathon
 */
public class SoundPlayer {
    
    private static SoundPlayer sp = new SoundPlayer();
    private SoundEffect[] impScreech;
    private SoundEffect[] mouseClick;
    private SoundEffect[] pageTurn;
    private SoundEffect phoneBeep;
    private SoundEffect paperWriting;
    private SoundEffect fireball;
    private SoundEffect faxMachine;
    private SoundEffect dialUpLong;
    private SoundEffect coffeeBrewing;
    
    public static void initSoundPlayer(AssetManager assets) {
        sp.init(assets);
    }
    
    public static SoundPlayer getSoundPlayer() { return sp; }
    
    public void init(AssetManager assets) {
        impScreech = new SoundEffect[] {
            assets.getSoundEffect("impScreech1"),
            assets.getSoundEffect("impScreech2")
        };
        mouseClick = new SoundEffect[] {
            assets.getSoundEffect("mouseClick1"),
            assets.getSoundEffect("mouseClick2")
        };
        pageTurn = new SoundEffect[] {
            assets.getSoundEffect("pageTurn1"),
            assets.getSoundEffect("pageTurn2")
        };
        phoneBeep = assets.getSoundEffect("phoneBeep");
        paperWriting = assets.getSoundEffect("paperWriting");
        fireball = assets.getSoundEffect("fireball");
        faxMachine = assets.getSoundEffect("faxMachine");
        dialUpLong = assets.getSoundEffect("dialUpLong");
        coffeeBrewing = assets.getSoundEffect("coffeeBrewing");
    }
    
    public void playRandomSound(SoundEffect[] sounds) {
        int index = (int)(Math.random()*sounds.length);
        sounds[index].play();
    }
    
    public void playImpScreech() {
        playRandomSound(impScreech);
    }
    
    public void playMouseClick() {
        playRandomSound(mouseClick);
    }
    
    public void playPageTurn() {
        playRandomSound(pageTurn);
    }
    
    public void playPhoneBeep() {
        phoneBeep.play();
    }
    
    public void playPaperWriting() {
        paperWriting.play();
    }
    
    public void playFireball() {
        fireball.play();
    }
    
    public void playFaxMachine() {
        faxMachine.play();
    }
    
    public void playDialUpLong() {
        dialUpLong.play();
    }
    
    public void playCoffeeBrewing() {
        coffeeBrewing.play();
    }
}
