/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ggj16;

import ggj16.sound.SoundPlayer;
import bropals.lib.simplegame.AWTGameWindow;
import bropals.lib.simplegame.GameStateRunner;
import bropals.lib.simplegame.GameWindow;
import bropals.lib.simplegame.io.AssetManager;
import ggj16.states.FiredState;
import ggj16.states.MenuState;
import ggj16.states.WinState;
import java.io.File;

/**
 *
 * @author Pants
 */
public class GGJ16 {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        AssetManager assetManager = new AssetManager(GGJ16.class, true);
        GameWindow window = new AWTGameWindow("Office Demon", 800, 600);
        assetManager.loadImage("assets/images/background/background.png", "background");
        assetManager.loadImage("assets/images/background/border.png", "border");
        assetManager.loadImage("assets/images/background/border2.png", "border2");
        assetManager.loadImage("assets/images/background/cutscreenDemon.png", "cutscreenDemon");
        assetManager.loadImage("assets/images/background/cutscreenPaper.png", "cutscreenPaper");
        assetManager.loadImage("assets/images/background/fireScreenFinal.png", "fireScreenFinal");
        assetManager.loadImage("assets/images/background/foreground.png", "foreground");
        assetManager.loadImage("assets/images/background/instructions.png", "instructions");
        assetManager.loadImage("assets/images/background/menuscreen.png", "menuscreen");
        assetManager.loadImage("assets/images/background/winScreenFinal.png", "winScreenFinal");
        assetManager.loadImage("assets/images/boss.png", "boss");
        assetManager.loadImage("assets/images/clock/clock1.png", "clock1");
        assetManager.loadImage("assets/images/clock/clock10.png", "clock10");
        assetManager.loadImage("assets/images/clock/clock11.png", "clock11");
        assetManager.loadImage("assets/images/clock/clock12.png", "clock12");
        assetManager.loadImage("assets/images/clock/clock2.png", "clock2");
        assetManager.loadImage("assets/images/clock/clock3.png", "clock3");
        assetManager.loadImage("assets/images/clock/clock4.png", "clock4");
        assetManager.loadImage("assets/images/clock/clock5.png", "clock5");
        assetManager.loadImage("assets/images/clock/clock6.png", "clock6");
        assetManager.loadImage("assets/images/clock/clock7.png", "clock7");
        assetManager.loadImage("assets/images/clock/clock8.png", "clock8");
        assetManager.loadImage("assets/images/clock/clock9.png", "clock9");
        assetManager.loadImage("assets/images/clock/Thumbs.db", "Thumbs");
        assetManager.loadImage("assets/images/controlPrompts/1KeyPrompt.png", "1KeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/4KeyPrompt.png", "4KeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/6KeyPrompt.png", "6KeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/9KeyPrompt.png", "9KeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/aKeyPrompt.png", "aKeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/brewingPrompt.png", "brewingPrompt");
        assetManager.loadImage("assets/images/controlPrompts/canNotDoYet.png", "canNotDoYet");
        assetManager.loadImage("assets/images/controlPrompts/deliveringPrompt.png", "deliveringPrompt");
        assetManager.loadImage("assets/images/controlPrompts/dKeyPrompt.png", "dKeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/faxingPrompt.png", "faxingPrompt");
        assetManager.loadImage("assets/images/controlPrompts/firePrompt.png", "firePrompt");
        assetManager.loadImage("assets/images/controlPrompts/fKeyPrompt.png", "fKeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/mKeyPrompt.png", "mKeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/nKeyPrompt.png", "nKeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/oKeyPrompt.png", "oKeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/pKeyPrompt.png", "pKeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/rKeyPrompt.png", "rKeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/scarePrompt.png", "scarePrompt");
        assetManager.loadImage("assets/images/controlPrompts/sKeyPrompt.png", "sKeyPrompt");
        assetManager.loadImage("assets/images/controlPrompts/taskCompletePrompt.png", "taskCompletePrompt");
        assetManager.loadImage("assets/images/controlPrompts/wakeUpPrompt.png", "wakeUpPrompt");
        assetManager.loadImage("assets/images/cutscreenFireDemon.png", "cutscreenFireDemon");
        assetManager.loadImage("assets/images/cutscreenFirePaper.png", "cutscreenFirePaper");
        assetManager.loadImage("assets/images/dayComplete.png", "dayComplete");
        assetManager.loadImage("assets/images/dayLabels/day1.png", "day1");
        assetManager.loadImage("assets/images/dayLabels/day2.png", "day2");
        assetManager.loadImage("assets/images/dayLabels/day3.png", "day3");
        assetManager.loadImage("assets/images/dayLabels/day4.png", "day4");
        assetManager.loadImage("assets/images/dayLabels/day5.png", "day5");
        assetManager.loadImage("assets/images/dayLabels/day6.png", "day6");
        assetManager.loadImage("assets/images/demon/demon1.png", "demon1");
        assetManager.loadImage("assets/images/demon/demon2.png", "demon2");
        assetManager.loadImage("assets/images/demon/demon3.png", "demon3");
        assetManager.loadImage("assets/images/furniture/coffeemaker.png", "coffeemaker");
        assetManager.loadImage("assets/images/furniture/computerdesk.png", "computerdesk");
        assetManager.loadImage("assets/images/furniture/faxMachine.png", "faxMachine");
        assetManager.loadImage("assets/images/furniture/meetingDoor.png", "meetingDoor");
        assetManager.loadImage("assets/images/furniture/paperdesk.png", "paperdesk");
        assetManager.loadImage("assets/images/furniture/phoneOffice.png", "phoneOffice");
        assetManager.loadImage("assets/images/furniture/portalOffice.png", "portalOffice");
        assetManager.loadImage("assets/images/furniture/workerDesk.png", "workerDesk");
        assetManager.loadImage("assets/images/impAlert.png", "impAlert");
        assetManager.loadImage("assets/images/keyBackground.png", "keyBackground");
        assetManager.loadImage("assets/images/list.png", "list");
        assetManager.loadImage("assets/images/paperstack/paperstack1.png", "paperstack1");
        assetManager.loadImage("assets/images/paperstack/paperstack2.png", "paperstack2");
        assetManager.loadImage("assets/images/paperstack/paperstack3.png", "paperstack3");
        assetManager.loadImage("assets/images/paperstack/paperstack4.png", "paperstack4");
        assetManager.loadImage("assets/images/paperstack/paperstack5.png", "paperstack5");
        assetManager.loadImage("assets/images/statusBackground.png", "statusBackground");
        assetManager.loadImage("assets/images/task/coffeePot/coffeepot1.png", "coffeepot1");
        assetManager.loadImage("assets/images/task/coffeePot/coffeepot2.png", "coffeepot2");
        assetManager.loadImage("assets/images/task/coffeePot/coffeepot3.png", "coffeepot3");
        assetManager.loadImage("assets/images/task/coffeePot/coffeepot4.png", "coffeepot4");
        assetManager.loadImage("assets/images/task/coffeePot/coffeepotBackground.png", "coffeepotBackground");
        assetManager.loadImage("assets/images/task/email/emailBackground.png", "emailBackground");
        assetManager.loadImage("assets/images/task/email/emailFinish.png", "emailFinish");
        assetManager.loadImage("assets/images/task/email/readScreen.png", "readScreen");
        assetManager.loadImage("assets/images/task/email/respondScreen.png", "respondScreen");
        assetManager.loadImage("assets/images/task/email/Thumbs.db", "Thumbs");
        assetManager.loadImage("assets/images/task/fax/faxMachineDialed.png", "faxMachineDialed");
        assetManager.loadImage("assets/images/task/fax/faxMachineTask.png", "faxMachineTask");
        assetManager.loadImage("assets/images/task/interview/applicationPaper1.png", "applicationPaper1");
        assetManager.loadImage("assets/images/task/interview/applicationPaper2.png", "applicationPaper2");
        assetManager.loadImage("assets/images/task/interview/applicationPaperAccept.png", "applicationPaperAccept");
        assetManager.loadImage("assets/images/task/interview/workerInterview.png", "workerInterview");
        assetManager.loadImage("assets/images/task/killImp/fireball.png", "fireball");
        assetManager.loadImage("assets/images/task/killImp/troublemaker1.png", "troublemaker1");
        assetManager.loadImage("assets/images/task/killImp/troublemaker2.png", "troublemaker2");
        assetManager.loadImage("assets/images/task/meeting/boo.png", "boo");
        assetManager.loadImage("assets/images/task/meeting/marker.png", "marker");
        assetManager.loadImage("assets/images/task/meeting/meetingBackground.png", "meetingBackground");
        assetManager.loadImage("assets/images/task/meeting/speech.png", "speech");
        assetManager.loadImage("assets/images/task/meeting/Thumbs.db", "Thumbs");
        assetManager.loadImage("assets/images/task/paperwork/paperTask1.png", "paperTask1");
        assetManager.loadImage("assets/images/task/paperwork/paperTask2.png", "paperTask2");
        assetManager.loadImage("assets/images/task/paperwork/paperTaskBackground.png", "paperTaskBackground");
        assetManager.loadImage("assets/images/task/scare/demonTinyTask.png", "demonTinyTask");
        assetManager.loadImage("assets/images/task/scare/houseIsScared.png", "houseIsScared");
        assetManager.loadImage("assets/images/task/scare/houseNotScared.png", "houseNotScared");
        assetManager.loadImage("assets/images/task/takeLunch/deliveryManSmall.png", "deliveryManSmall");
        assetManager.loadImage("assets/images/task/takeLunch/phonePickUpLunchTask.png", "phonePickUpLunchTask");
        assetManager.loadImage("assets/images/task/takeLunch/phoneSetDownLunchTask.png", "phoneSetDownLunchTask");
        assetManager.loadImage("assets/images/task/takeLunch/roadLunchTask.png", "roadLunchTask");
        assetManager.loadImage("assets/images/Thumbs.db", "Thumbs");
        assetManager.loadImage("assets/images/worker/emptyImage.png", "emptyImage");
        assetManager.loadImage("assets/images/worker/worker.png", "worker");
        assetManager.loadImage("assets/images/worker/workerAsleep.png", "workerAsleep");
        assetManager.loadImage("assets/images/worker/workerAwake.png", "workerAwake");
        assetManager.loadImage("assets/images/worker/workerHeadAwake.png", "workerHeadAwake");
        assetManager.loadImage("assets/images/worker/workerHeadDead.png", "workerHeadDead");
        assetManager.loadImage("assets/images/worker/workerHeadSleeping.png", "workerHeadSleeping");
        assetManager.loadSoundEffect("assets/sounds/approaching.wav", "approaching");
        assetManager.loadSoundEffect("assets/sounds/bossDemon1.wav", "bossDemon1");
        assetManager.loadSoundEffect("assets/sounds/bossDemon2.wav", "bossDemon2");
        assetManager.loadSoundEffect("assets/sounds/bossDemon3.wav", "bossDemon3");
        assetManager.loadSoundEffect("assets/sounds/bossDemonGrowl.wav", "bossDemonGrowl");
        assetManager.loadSoundEffect("assets/sounds/coffeeBrewing.wav", "coffeeBrewing");
        assetManager.loadSoundEffect("assets/sounds/dialUpLong.wav", "dialUpLong");
        assetManager.loadSoundEffect("assets/sounds/faxMachine.wav", "faxMachine");
        assetManager.loadSoundEffect("assets/sounds/fireball.wav", "fireball");
        assetManager.loadSoundEffect("assets/sounds/impScreech1.wav", "impScreech1");
        assetManager.loadSoundEffect("assets/sounds/impScreech2.wav", "impScreech2");
        assetManager.loadSoundEffect("assets/sounds/laugh1.wav", "laugh1");
        assetManager.loadSoundEffect("assets/sounds/laugh2.wav", "laugh2");
        assetManager.loadSoundEffect("assets/sounds/laugh3.wav", "laugh3");
        assetManager.loadSoundEffect("assets/sounds/lunchArrived.wav", "lunchArrived");
        assetManager.loadSoundEffect("assets/sounds/mouseClick1.wav", "mouseClick1");
        assetManager.loadSoundEffect("assets/sounds/mouseClick2.wav", "mouseClick2");
        assetManager.loadSoundEffect("assets/sounds/pageTurn1.wav", "pageTurn1");
        assetManager.loadSoundEffect("assets/sounds/pageTurn2.wav", "pageTurn2");
        assetManager.loadSoundEffect("assets/sounds/paperWriting.wav", "paperWriting");
        assetManager.loadSoundEffect("assets/sounds/phoneBeep.wav", "phoneBeep");
        assetManager.loadSoundEffect("assets/sounds/stomp.wav", "stomp");
        assetManager.loadSoundEffect("assets/sounds/wakeUp.wav", "wakeUp");
        assetManager.loadSoundEffect("assets/sounds/wilhelm_scream.wav", "wilhelm_scream");
        assetManager.loadSoundEffect("assets/music/loserMusic.wav", "loserMusic");
        assetManager.loadSoundEffect("assets/music/mainSong.wav", "mainSong");
        assetManager.loadSoundEffect("assets/music/victory.wav", "victory");
        
        SoundPlayer.getSoundPlayer().init(assetManager);
        
        GameStateRunner runner = new GameStateRunner(window, assetManager);
        runner.setState(new MenuState());
        runner.loop();
    }

}
