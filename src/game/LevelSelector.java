package game;

import city.cs.engine.*;
import org.jbox2d.common.Vec2;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * LevelSelector class - screen where players can choose
 * different game levels by clicking on the buttons and also
 * includes an option to exit the game.
 * Also displays game rules on the screen.
 */
public class LevelSelector extends WorldWithBackground {
    private GameView view;
    private final BirdGame game;
    //shapes for level buttons and exit button
    private static final Shape shape = new PolygonShape(-2.02f, 0.43f, -2.13f, -0.23f, -0.83f, -1.94f, -0.21f, -1.95f, 2.11f, -0.83f, 2.05f, 0.91f, 0.96f, 1.94f, -0.78f, 1.57f);
    private static final Shape exitShape = new PolygonShape(-2.33f, 1.55f, -3.14f, -0.03f, -2.28f, -1.42f, 2.27f, -1.48f, 3.13f, -0.01f, 2.27f, 1.53f);
    //passive and active img
    private static final String[] passiveImagePaths = {"data/level1/bird.png", "data/level2/dessertBird.png", "data/level3/moonBird.png"};
    private static final String[] activeImagePaths = {"data/level1/birdFlyUp.png", "data/level2/dessertBirdFlyUp.png", "data/level3/moonBirdFlyUp.png"};
    private static final String passiveExitImagePath = "data/exit.png";
    private static final String activeExitImagePath = "data/exit.png";
    //game rules img
    private static final String gameRules = "data/rules.png";
    //img sizes
    private static final int imageHeight = 4;
    private static final float activeImageHeight = 4.2f;
    //positions of level buttons
    private static final float[][] positions = {
            {-24, 2}, //level 1
            {-2, -4}, //level 2
            {22, 4} //level 3
    };
    //exit button position
    private static final float[] exitPosition = {28, 16};
    //list of images for passive (def) and active states
    private final List<BodyImage> activeImages = new ArrayList<>(activeImagePaths.length);
    private final List<BodyImage> passiveImages = new ArrayList<>(passiveImagePaths.length);

    private int exitBodyIndex;
    private int lastIndex = -1;

    /**
     * Handles mouse interactions:
     * Level selection and exit.
     */
    private final MouseAdapter mouseHandler = new MouseAdapter() {
        private int activeIndex = -1;

        /**
         * when clicking mouse - start level / exit game depends on the index of the body
         *
         * @param e the event to be processed
         */
        public void mouseClicked(MouseEvent e) {
            int index = LevelSelector.this.getBodyIndexUnderPoint(view.viewToWorld(e.getPoint()));
            System.out.println("clicked on " + index);
            if (index != -1) {
                LevelSelector.this.stop(); // Stops the current world (level)
                index = lastIndex - index; // Reverse index order
                if (index == exitBodyIndex)
                    game.exitGame(); // Exit the game if the exit button is clicked
                else
                    game.startLevel(index); // Start the selected level
            }
        }

        /**
         * Handles mouse movement:
         * when moving mouse throw the body - changing passive image to active and vice versa
         *
         * @param e the event to be processed
         */
        public void mouseMoved(MouseEvent e) {
            int index = LevelSelector.this.getBodyIndexUnderPoint(view.viewToWorld(e.getPoint()));
            if (index != -1) {
                if ((activeIndex != -1) && (activeIndex != index))
                    LevelSelector.this.makePassiveBody(activeIndex); // Reset the previous active button
                LevelSelector.this.makeActiveBody(index); // Change the button to its active image
                activeIndex = index;
            } else {
                if (activeIndex != -1)
                    LevelSelector.this.makePassiveBody(activeIndex); // Reset the active button when the mouse leaves
                activeIndex = -1;
            }
        }

        @Override
        public void mouseEntered(MouseEvent e) {
            view.requestFocus(); //view has focus for input
        }
    };

    /**
     * Constructor to initialize the level selector, buttons, add images
     *
     * @param game Main game instance used for starting levels or exit the game
     */
    public LevelSelector(BirdGame game) {
        super();
        this.game = game;
        addGameRulesImage(); //game rules img add
        background = new ImageIcon("data/introBackground.jpg").getImage();

        // Create buttons (w/ passive images) for level selection and set their positions
        for (int i = 0; i < passiveImagePaths.length; i++) {
            var button = new StaticBody(this, shape);
            button.setPosition(new Vec2(positions[i][0], positions[i][1]));
            BodyImage image = new BodyImage(passiveImagePaths[i], imageHeight);
            button.addImage(image);
            passiveImages.add(image);
        }
        // adding active imgs for buttons (will be used when mouse is over button)
        for (String imagePath : activeImagePaths)
            activeImages.add(new BodyImage(imagePath, imageHeight));

        // create exit button (if image for it exists)
        if (passiveExitImagePath != null) {
            exitBodyIndex = passiveImagePaths.length; // exit button is last in the list
            var button = new StaticBody(this, exitShape);
            button.setPosition(new Vec2(exitPosition[0], exitPosition[1]));
            BodyImage image = new BodyImage(passiveExitImagePath, imageHeight);
            button.addImage(image);
            passiveImages.add(image);
        }
        // adding active image for exit button
        if (activeExitImagePath != null) {
            activeImages.add(new BodyImage(activeExitImagePath, activeImageHeight));
        }

        //reverse list (matching indexes and images)
        Collections.reverse(activeImages);
        Collections.reverse(passiveImages);
        lastIndex = passiveImages.size() - 1;
    }

    /**
     * Setter method for the view
     *
     * @param view - game view
     */
    public void setView(GameView view) {
        this.view = view;
    }

    /**
     * method to return mouse handler
     *
     * @return MouseAdapter
     */
    public MouseAdapter getMouseHandler() {
        return mouseHandler;
    }

    /**
     * returning index of body when mouse is on this body
     * if mouse (point) is not on the body - return -1
     *
     * @param point - checking coordinates
     * @return index of body or -1
     */
    private int getBodyIndexUnderPoint(Vec2 point) {
        List<StaticBody> bodies = getStaticBodies();
        for (var i = 0; i < bodies.size(); i++)
            if (bodies.get(i).contains(point)) {
                return i; // Return the index of the button if the mouse is over it
            }
        return -1; // Return -1 if no button is under the mouse
    }

    /**
     * changing button img to active img at index (hover)
     *
     * @param index of button
     */
    private void makeActiveBody(int index) {
        if ((0 <= index) && (index < activeImages.size())) {
            Body body = getStaticBodies().get(index);
            body.removeAllImages();
            body.addImage(activeImages.get(index));
        }
    }

    /**
     * changing button img to passive img at index (default)
     *
     * @param index of button
     */
    private void makePassiveBody(int index) {
        if ((0 <= index) && (index < passiveImages.size())) {
            Body body = getStaticBodies().get(index);
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
