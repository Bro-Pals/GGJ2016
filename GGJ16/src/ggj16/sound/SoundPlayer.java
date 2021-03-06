/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16.sound;

import bropals.lib.simplegame.io.AssetManager;
import bropals.lib.simplegame.sound.SoundEffect;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineEvent;
import javax.sound.sampled.LineListener;

/**
 *
 * @author Jonathon
 */
public class SoundPlayer {

    public static final int MAIN_SONG = 0, VICTORY_SONG = 1, LOSER_SONG = 2, NONE = 3;

    private static SoundPlayer sp = new SoundPlayer();
    private SoundEffect[] impScreech;
    private SoundEffect[] mouseClick;
    private SoundEffect[] pageTurn;
    private SoundEffect[] laughing;
    private SoundEffect[] talking;
    private SoundEffect[] scared;
    private SoundEffect phoneBeep;
    private SoundEffect paperWriting;
    private SoundEffect fireball;
    private SoundEffect faxMachine;
    private SoundEffect dialUpLong;
    private SoundEffect coffeeBrewing;
    private SoundEffect impApproaching;
    private SoundEffect wakeUp;
    private SoundEffect lunchArrived;
    private SoundEffect stomp;

    //Music
    private SoundEffect mainSong;
    private SoundEffect victorySong;
    private SoundEffect loseSong;

    private SoundEffect currentMusicTrack;
    private int currentMusic = -1;

    public static void initSoundPlayer(AssetManager assets) {
        sp.init(assets);
    }

    public static SoundPlayer getSoundPlayer() {
        return sp;
    }

    public void init(AssetManager assets) {
        impScreech = new SoundEffect[]{
            assets.getSoundEffect("impScreech1"),
            assets.getSoundEffect("impScreech2")
        };
        mouseClick = new SoundEffect[]{
            assets.getSoundEffect("mouseClick1"),
            assets.getSoundEffect("mouseClick2")
        };
        pageTurn = new SoundEffect[]{
            assets.getSoundEffect("pageTurn1"),
            assets.getSoundEffect("pageTurn2")
        };
        laughing = new SoundEffect[] {
            assets.getSoundEffect("laugh1"),
            assets.getSoundEffect("laugh2"),
            assets.getSoundEffect("laugh3")
        };
        talking = new SoundEffect[] {
            assets.getSoundEffect("bossDemon1"),
            assets.getSoundEffect("bossDemon2"),
            assets.getSoundEffect("bossDemon3")
        };
        scared = new SoundEffect[] {
            assets.getSoundEffect("wilhelm_scream")
        };
        phoneBeep = assets.getSoundEffect("phoneBeep");
        paperWriting = assets.getSoundEffect("paperWriting");
        fireball = assets.getSoundEffect("fireball");
        faxMachine = assets.getSoundEffect("faxMachine");
        dialUpLong = assets.getSoundEffect("dialUpLong");
        coffeeBrewing = assets.getSoundEffect("coffeeBrewing");
        impApproaching = assets.getSoundEffect("approaching");

        mainSong = assets.getSoundEffect("mainSong");
        victorySong = assets.getSoundEffect("victory");
        loseSong = assets.getSoundEffect("loserMusic");
        wakeUp = assets.getSoundEffect("wakeUp");
        lunchArrived = assets.getSoundEffect("lunchArrived");
        stomp = assets.getSoundEffect("stomp");
        
        currentMusicTrack = mainSong;
    }

    public void playVictoryMusic() {
        victorySong.play();
    }

    
    
    public void setMusicTo(int song) {
        if (currentMusicTrack != null && currentMusic != song) {
            currentMusicTrack.getRaw().stop();
            currentMusic = song;
            if (song == MAIN_SONG) {
                currentMusicTrack = mainSong;
            } else if (song == VICTORY_SONG) {
                currentMusicTrack = victorySong;
            } else if (song == LOSER_SONG) {
                currentMusicTrack = loseSong;
            }
            // don't play anything if there is no song
            if (song != NONE) {
                currentMusicTrack.getRaw().loop(Clip.LOOP_CONTINUOUSLY);
            }
        }
    }

    public void playRandomSound(SoundEffect[] sounds) {
        if (sounds == null) {
            System.out.println("Sound is null");
        }
        int index = (int) (Math.random() * sounds.length);
        sounds[index].play();
    }
    
    public void playDemonSpeaking() {
        playRandomSound(talking);
    }
    
    public void playScareHouse() {
        playRandomSound(scared);
    }
    
    public void playStamp() {
        stomp.play();
    }
    
    public void playWakeUpEmployee() {
        wakeUp.play();
    }
    
    public void playFallAsleepEmployee() {
        //System.out.println("No fall asleep employee yet");
    }
    
    public void playFoodArrival() {
        lunchArrived.play();
    }

    public void playLaugh() {
        playRandomSound(laughing);
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
    
    public void playImpApproaching() {
        impApproaching.play();
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

    public void stopFaxMachine() {
        faxMachine.getRaw().stop();
    }

    public void playDialUpLong() {
        dialUpLong.play();
    }

    public void stopDialUpLong() {
        dialUpLong.getRaw().stop();
    }

    public void playCoffeeBrewing() {
        coffeeBrewing.play();
    }
}
