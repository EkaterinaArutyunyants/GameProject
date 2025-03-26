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

public class LevelSelector extends World  {
    private GameView view;
    private final BirdGame game;
    private static final Image background = new ImageIcon("data/startBackground.jpeg").getImage();
    private static final Shape shape = new PolygonShape(-2.02f, 0.43f, -2.13f, -0.23f, -0.83f, -1.94f, -0.21f, -1.95f, 2.11f, -0.83f, 2.05f, 0.91f, 0.96f, 1.94f, -0.78f, 1.57f);
    private static final String [] passiveImagePaths ={"data/level1/bird.png","data/level2/dessertBird.png","data/level3/moonBird.png"};
    private static final String [] activeImagePaths ={"data/level1/birdFlyUp.png","data/level2/dessertBirdFlyUp.png","data/level3/moonBirdFlyUp.png"};
    private static final String passiveExitImagePath ="data/level2/cloud.png";
    private static final String  activeExitImagePath ="data/level2/cloud.png";
    private static final int imageHeight=4;
    private final List<BodyImage> activeImages = new ArrayList<>(activeImagePaths.length);
    private final List<BodyImage> passiveImages = new ArrayList<>(passiveImagePaths.length);
    private int exitBodyIndex = -1;
    private int lastIndex = -1;

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

    public LevelSelector(BirdGame game){
        super();
        this.game = game;
        float x = -7, y = 7;
        for (String imagePath : passiveImagePaths){
            var button = new StaticBody(this, shape);
            button.setPosition(new Vec2(x,y));
            x+=3f;
            y-=3f;
            BodyImage image =new BodyImage(imagePath, imageHeight);
            button.addImage(image);
            button.setAlwaysOutline(true);
            passiveImages.add(image);
        }
        for (String imagePath : activeImagePaths)
            activeImages.add(new BodyImage(imagePath, imageHeight));

        if (passiveExitImagePath != null) {
            exitBodyIndex = passiveImagePaths.length;
            var button = new StaticBody(this, shape);
            button.setPosition(new Vec2(x,y));
            BodyImage image =new BodyImage(passiveExitImagePath, imageHeight);
            button.addImage(image);
            button.setAlwaysOutline(true);
            passiveImages.add(image);
        }
        if (activeExitImagePath !=null) {
            activeImages.add(new BodyImage(activeExitImagePath, imageHeight));
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

    public static Image getBackground() {
        return background;
    }

    private int getBodyIndexUnderPoint(Vec2 point){
        List<StaticBody> bodies = getStaticBodies();
        for (var i=0; i < bodies.size(); i++)
            if (bodies.get(i).contains(point)) {
                return i;
            }
        return -1;
    }

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
}
