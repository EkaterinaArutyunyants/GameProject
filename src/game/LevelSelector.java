package game;

import city.cs.engine.Body;
import city.cs.engine.BodyImage;
import city.cs.engine.PolygonShape;
import city.cs.engine.Shape;
import city.cs.engine.StaticBody;
import city.cs.engine.World;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class LevelSelector extends WorldWithBackground  {
    private GameView view;
    private final BirdGame game;
    private static final Shape shape = new PolygonShape(-2.02f, 0.43f, -2.13f, -0.23f, -0.83f, -1.94f, -0.21f, -1.95f, 2.11f, -0.83f, 2.05f, 0.91f, 0.96f, 1.94f, -0.78f, 1.57f);
    private static final Shape exitShape = new PolygonShape(-2.33f,1.55f, -3.14f,-0.03f, -2.28f,-1.42f, 2.27f,-1.48f, 3.13f,-0.01f, 2.27f,1.53f);
    private static final String [] passiveImagePaths ={"data/level1/bird.png","data/level2/dessertBird.png","data/level3/moonBird.png"};
    private static final String [] activeImagePaths ={"data/level1/birdFlyUp.png","data/level2/dessertBirdFlyUp.png","data/level3/moonBirdFlyUp.png"};
    private static final String passiveExitImagePath ="data/exit.png";
    private static final String activeExitImagePath ="data/exit.png";
    private static final String gameRules = "data/rules.png";
    private static final int imageHeight = 4;
    private static final float activeImageHeight = 4.2f;
    private static final float[][] positions = {
            {-24, 2},
            {-2, -4},
            {22, 4}
    };
    private static final float[] exitPosition = {28, 16};
    private final List<BodyImage> activeImages = new ArrayList<>(activeImagePaths.length);
    private final List<BodyImage> passiveImages = new ArrayList<>(passiveImagePaths.length);
    private int exitBodyIndex;
    private int lastIndex = -1;

    /**
     * when clicking mouse - start level / exit game depends on th index of the body
     */
    private final MouseAdapter mouseHandler  = new MouseAdapter() {
        private int activeIndex = -1;
        public void mouseClicked(MouseEvent e) {
            int index = LevelSelector.this.getBodyIndexUnderPoint(view.viewToWorld(e.getPoint()));
            System.out.println("clicked on "+index);
            if (index != -1) {
                LevelSelector.this.stop();
                index = lastIndex-index;
                if (index==exitBodyIndex)
                    game.exitGame();
                else
                    game.startLevel(index);
            }
        }

        /**
         * when moving mouse throw the body - changing passive image to active and vice versa
         * @param e the event to be processed
         */
        public void mouseMoved(MouseEvent e){
            int index = LevelSelector.this.getBodyIndexUnderPoint(view.viewToWorld(e.getPoint()));
            if (index!=-1){
                if ((activeIndex !=-1) && (activeIndex !=index))
                    LevelSelector.this.makePassiveBody(activeIndex);
                LevelSelector.this.makeActiveBody(index);
                activeIndex = index;
            } else{
                if (activeIndex !=-1)
                    LevelSelector.this.makePassiveBody(activeIndex);
                activeIndex =-1;
            }
        }
        @Override
        public void mouseEntered(MouseEvent e) {
            view.requestFocus();
        }
    };

    /**
     * setting buttons positions, adding images
     * @param game
     */
    public LevelSelector(BirdGame game){
        super();
        this.game = game;
        background = new ImageIcon("data/introBackground.jpg").getImage();
        addGameRulesImage();
        for (int i = 0; i < passiveImagePaths.length; i++) {
            var button = new StaticBody(this, shape);
            button.setPosition(new Vec2(positions[i][0], positions[i][1]));
            BodyImage image =new BodyImage(passiveImagePaths[i], imageHeight);
            button.addImage(image);
            button.setAlwaysOutline(true);
            passiveImages.add(image);
        }
        for (String imagePath : activeImagePaths)
            activeImages.add(new BodyImage(imagePath, imageHeight));

        if (passiveExitImagePath != null) {
            exitBodyIndex = passiveImagePaths.length;
            var button = new StaticBody(this, exitShape);
            button.setPosition(new Vec2(exitPosition[0], exitPosition[1]));
            BodyImage image =new BodyImage(passiveExitImagePath, imageHeight);
            button.addImage(image);
            button.setAlwaysOutline(true);
            passiveImages.add(image);
        }
        if (activeExitImagePath !=null) {
            activeImages.add(new BodyImage(activeExitImagePath, activeImageHeight));
        }
        Collections.reverse(activeImages);
        Collections.reverse(passiveImages);
        lastIndex = passiveImages.size()-1;
    }

    public void setView(GameView view) {
        this.view = view;
    }

    public MouseAdapter getMouseHandler() {
        return mouseHandler;
    }

    /**
     * returning index of body when mouse is on this body
     * if mouse (point) is not on the body - return -1
     * @param point
     * @return
     */
    private int getBodyIndexUnderPoint(Vec2 point){
        List<StaticBody> bodies = getStaticBodies();
        for (var i=0; i < bodies.size(); i++)
            if (bodies.get(i).contains(point)) {
                return i;
            }
        return -1;
    }

    /**
     * changing images when moving mouse
     * @param index
     */
    private void makeActiveBody(int index){
        if ((0<=index) &&(index< activeImages.size())){
            Body body =  getStaticBodies().get(index);
            body.removeAllImages();
            body.addImage(activeImages.get(index));
        }
    }
    private void makePassiveBody(int index){
        if ((0<=index) &&(index< passiveImages.size())){
            Body body =  getStaticBodies().get(index);
            body.removeAllImages();
            body.addImage(passiveImages.get(index));
        }
    }

    /**
     * method for adding image of rules for the game
     */
    private void addGameRulesImage() {
        var rulesBody = new StaticBody(this);
        rulesBody.setPosition(new Vec2(-27, -17));
        BodyImage rulesImage = new BodyImage(gameRules, 10);
        rulesBody.addImage(rulesImage);
    }

}
