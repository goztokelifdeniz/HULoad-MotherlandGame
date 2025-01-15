import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;


public class Main extends Application{
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage){

        DrillerMachine drillerMachine = new DrillerMachine();
        File undergroundDir = new File("assets/underground");
        File[] underground_files_array = undergroundDir.listFiles();

        File drill_image_left_file = new File("assets/drill/drill_01.png");
        File drill_image_down_file = new File("assets/drill/drill_45.png");
        File drill_image_up_file = new File("assets/drill/drill_27.png");

        Image drill_image_left = new Image(drill_image_left_file.getPath());
        Image drill_image_down = new Image(drill_image_down_file.getPath());
        Image drill_image_up = new Image(drill_image_up_file.getPath());

        ArrayList<Obstacle> obstacle_objects = new ArrayList<>();
        ArrayList<Soil> soil_objects = new ArrayList<>();
        ArrayList<Valuable> valuable_objects = new ArrayList<>();
        ArrayList<Ground> ground_objects = new ArrayList<>();

        // Hard-coding the valueables
        for (File current_file : underground_files_array){
            String file_name = current_file.getName();
            String file_path = current_file.getPath();
            if (file_name.contains("valuable")){
                Valuable current_valuable = new Valuable();
                current_valuable.setPath(file_path);
                valuable_objects.add(current_valuable);
                ground_objects.add(current_valuable);
            } else if (file_name.contains("soil")) {
                Soil current_soil = new Soil();
                current_soil.setPath(file_path);
                soil_objects.add(current_soil);
                ground_objects.add(current_soil);
            } else if (file_name.contains("lava")) {
                Lava curr_lava = new Lava();
                curr_lava.setPath(file_path);
                ground_objects.add(curr_lava);
            } else if (file_name.contains("obstacle")) {
                Obstacle curr_obst = new Obstacle();
                curr_obst.setPath(file_path);
                obstacle_objects.add(curr_obst);
                ground_objects.add(curr_obst);
            }
        }

        GridPane first_gridpane = new GridPane();
        set_background_colors(first_gridpane);
        HashMap<Integer, Cell> last_hashmap = new HashMap<>();
        set_all_gridpane_cells(first_gridpane, last_hashmap, obstacle_objects, ground_objects, soil_objects);

        for (Cell cell : last_hashmap.values()){
            cell.getImageView().setFitWidth(50);
            cell.getImageView().setFitHeight(50);
        }

        last_hashmap.get(42).getImageView().setImage(drill_image_left);
        Scene scene = new Scene(first_gridpane, 800, 600);
        Text variable_fuel = set_independent_texts(first_gridpane, drillerMachine);

        Scene green_game_over_scene = set_green_game_over_scene(drillerMachine);
        Scene red_game_over_scene = set_red_game_over_scene();

        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(1), e -> {
            if (drillerMachine.getFuel() < 0){
                primaryStage.setScene(green_game_over_scene);
                primaryStage.setTitle("HU-Load");
                primaryStage.show();

                drillerMachine.setFuel(drillerMachine.getFuel() - 1);
                if (drillerMachine.getFuel() < - 10000){
                    primaryStage.close();
                }
            } else {
                drillerMachine.setFuel(drillerMachine.getFuel() - 1);
                variable_fuel.setText(Double.toString(drillerMachine.getFuel()));
            }
        }));

        timeline.setCycleCount(Animation.INDEFINITE);
        timeline.play();

        move_driller(scene, red_game_over_scene, primaryStage,
                drill_image_left, drill_image_down, drill_image_up,
                drillerMachine, variable_fuel, last_hashmap);

        gravity_movement(scene, last_hashmap, drill_image_left, drill_image_down, drill_image_up);

        primaryStage.setScene(scene);
        primaryStage.setTitle("HU-Load");
        primaryStage.show();
    }







    public static void set_background_colors(GridPane first_gridpane){
        // Adding colors
        for (int index = 0; index < 3*16; index++){
            int row = index % 16;
            int column = index / 16;
            Rectangle sky_rectangle = new Rectangle();
            sky_rectangle.setWidth(50);
            sky_rectangle.setHeight(50);
            sky_rectangle.setX(row);
            sky_rectangle.setY(column);
            sky_rectangle.setFill(Color.LIGHTSKYBLUE);
            first_gridpane.add(sky_rectangle, row, column);
        }
        for (int index = 3*16; index < 16*12; index++){
            int row = index % 16;
            int column = index / 16;
            Rectangle ground_rectangle = new Rectangle();
            ground_rectangle.setWidth(50);
            ground_rectangle.setHeight(50);
            ground_rectangle.setX(row);
            ground_rectangle.setY(column);
            ground_rectangle.setFill(Color.NAVAJOWHITE);
            first_gridpane.add(ground_rectangle, row, column);
        }
    }
    public static Text set_independent_texts(GridPane first_gridpane, DrillerMachine drillerMachine){
        Text fuel_text = new Text("Fuel: ");
        fuel_text.setFont(new Font(15));
        fuel_text.setFill(Color.BLACK);
        first_gridpane.add(fuel_text,0,0);

        Text haul_label = new Text("Haul: ");
        haul_label.setFont(new Font(15));
        haul_label.setFill(Color.BLACK);
        first_gridpane.add(haul_label,0,1);

        Text money_label = new Text("Money: ");
        money_label.setFont(new Font(15));
        money_label.setFill(Color.BLACK);
        first_gridpane.add(money_label,0,2);

        Text variable_fuel = new Text(Double.toString(drillerMachine.getFuel()));
        variable_fuel.setFont(new Font(10));
        variable_fuel.setFill(Color.BLACK);
        first_gridpane.add(variable_fuel,1,0);

        Text variable_haul = new Text(Double.toString(drillerMachine.getHaul()));
        variable_haul.setFont(new Font(10));
        variable_haul.setFill(Color.BLACK);
        first_gridpane.add(variable_haul,1,1);

        Text variable_money = new Text(Double.toString(drillerMachine.getMoney()));
        variable_money.setFont(new Font(10));
        variable_money.setFill(Color.BLACK);
        first_gridpane.add(variable_money,1,2);
        return variable_fuel;
    }

    public static void move_driller(Scene scene, Scene red_game_over_scene, Stage primaryStage,
                                    Image drill_image_left, Image drill_image_down, Image drill_image_up,
                                    DrillerMachine drillerMachine, Text variable_fuel, HashMap<Integer, Cell> last_hashmap){

        scene.setOnKeyPressed(e -> {
            int current_place_index;
            KeyCode keyCode = e.getCode();
            if (keyCode == KeyCode.UP) {
                current_place_index = find_driller_index(last_hashmap, drill_image_left, drill_image_down, drill_image_up);

                int where_i_want_to_go_to = current_place_index - 16;
                Cell cell_i_will_go_to = last_hashmap.get(where_i_want_to_go_to);

                if (! (current_place_index / 16 == 0)){
                    if (cell_i_will_go_to.isIs_ground()){
                        if (cell_i_will_go_to.getImageView().getImage() == null){
                            last_hashmap.get(current_place_index).getImageView().setImage(null);
                            last_hashmap.get(where_i_want_to_go_to).getImageView().setImage(drill_image_up);

                            drillerMachine.setFuel(drillerMachine.getFuel() - 1000);
                            variable_fuel.setText(Double.toString(drillerMachine.getFuel()));
                        }
                    } else {
                        last_hashmap.get(current_place_index).getImageView().setImage(null);
                        last_hashmap.get(where_i_want_to_go_to).getImageView().setImage(drill_image_up);
                        drillerMachine.setFuel(drillerMachine.getFuel() - 1000);
                        variable_fuel.setText(Double.toString(drillerMachine.getFuel()));
                    }
                }

            } else if (keyCode == KeyCode.DOWN) {
                current_place_index = find_driller_index(last_hashmap, drill_image_left, drill_image_down, drill_image_up);

                int where_i_want_to_go_to = current_place_index + 16;
                Cell cell_i_will_go_to = last_hashmap.get(where_i_want_to_go_to);

                if (! (current_place_index / 16 == 11)){
                    if (cell_i_will_go_to.isIs_ground()){
                        if (cell_i_will_go_to.getGround().isDrillable()){
                            if ( ! cell_i_will_go_to.getGround().isKills()){
                                last_hashmap.get(current_place_index).getImageView().setImage(null);
                                cell_i_will_go_to.getImageView().setImage(drill_image_down);
                                drillerMachine.setIndex_machine_is_at(where_i_want_to_go_to);
                                drillerMachine.setFuel(drillerMachine.getFuel() - 1000);
                                variable_fuel.setText(Double.toString(drillerMachine.getFuel()));

                                if (cell_i_will_go_to.getGround().isGround_is_valuable()){
                                    drillerMachine.setHaul(drillerMachine.getHaul() + ((Valuable) cell_i_will_go_to.getGround()).getValuable_weight());
                                    drillerMachine.setMoney(drillerMachine.getMoney() + ((Valuable) cell_i_will_go_to.getGround()).getValuable_worth());
                                }
                            } else {
                                primaryStage.setScene(red_game_over_scene);
                                primaryStage.setTitle("HU-Load");
                                primaryStage.show();
                            }
                        }
                    } else {
                        last_hashmap.get(current_place_index).getImageView().setImage(null);
                        last_hashmap.get(where_i_want_to_go_to).getImageView().setImage(drill_image_down);
                        drillerMachine.setFuel(drillerMachine.getFuel() - 1000);
                        variable_fuel.setText(Double.toString(drillerMachine.getFuel()));
                    }
                }


            } else if (keyCode == KeyCode.LEFT) {
                current_place_index = find_driller_index(last_hashmap, drill_image_left, drill_image_down, drill_image_up);

                int where_i_want_to_go_to = current_place_index - 1;
                Cell cell_i_will_go_to = last_hashmap.get(where_i_want_to_go_to);

                if (where_i_want_to_go_to % 16 != 15){
                    if (cell_i_will_go_to.isIs_ground()){
                        if (cell_i_will_go_to.getGround().isDrillable()){
                            if ( ! cell_i_will_go_to.getGround().isKills()){
                                last_hashmap.get(current_place_index).getImageView().setImage(null);
                                cell_i_will_go_to.getImageView().setImage(drill_image_left);
                                cell_i_will_go_to.getImageView().setScaleX(1);
                                drillerMachine.setFuel(drillerMachine.getFuel() - 1000);
                                variable_fuel.setText(Double.toString(drillerMachine.getFuel()));

                                if (cell_i_will_go_to.getGround().isGround_is_valuable()){
                                    drillerMachine.setHaul(drillerMachine.getHaul() + ((Valuable) cell_i_will_go_to.getGround()).getValuable_weight());
                                    drillerMachine.setMoney(drillerMachine.getMoney() + ((Valuable) cell_i_will_go_to.getGround()).getValuable_worth());
                                }
                            } else {
                                primaryStage.setScene(red_game_over_scene);
                                primaryStage.setTitle("HU-Load");
                                primaryStage.show();
                            }
                        }
                    } else {
                        last_hashmap.get(current_place_index).getImageView().setImage(null);
                        last_hashmap.get(where_i_want_to_go_to).getImageView().setImage(drill_image_left);
                        cell_i_will_go_to.getImageView().setScaleX(1);
                        drillerMachine.setFuel(drillerMachine.getFuel() - 1000);
                        variable_fuel.setText(Double.toString(drillerMachine.getFuel()));
                    }
                }
            } else if (keyCode == KeyCode.RIGHT) {

                current_place_index = find_driller_index(last_hashmap, drill_image_left, drill_image_down, drill_image_up);
                int where_i_want_to_go_to = current_place_index + 1;
                Cell cell_i_will_go_to = last_hashmap.get(where_i_want_to_go_to);

                if (where_i_want_to_go_to % 16 != 0){
                    if (cell_i_will_go_to.isIs_ground()){
                        if (cell_i_will_go_to.getGround().isDrillable()){
                            if ( ! cell_i_will_go_to.getGround().isKills()){

                                last_hashmap.get(current_place_index).getImageView().setImage(null);

                                cell_i_will_go_to.getImageView().setImage(drill_image_left);
                                cell_i_will_go_to.getImageView().setScaleX(-1);
                                drillerMachine.setIndex_machine_is_at(where_i_want_to_go_to);
                                drillerMachine.setFuel(drillerMachine.getFuel() - 1000);
                                variable_fuel.setText(Double.toString(drillerMachine.getFuel()));

                                if (cell_i_will_go_to.getGround().isGround_is_valuable()){
                                    drillerMachine.setHaul(drillerMachine.getHaul() + ((Valuable) cell_i_will_go_to.getGround()).getValuable_weight());
                                    drillerMachine.setMoney(drillerMachine.getMoney() + ((Valuable) cell_i_will_go_to.getGround()).getValuable_worth());
                                }
                            } else {
                                primaryStage.setScene(red_game_over_scene);
                                primaryStage.setTitle("Mining Game");
                                primaryStage.show();
                            }
                        }
                    } else {
                        last_hashmap.get(current_place_index).getImageView().setImage(null);
                        last_hashmap.get(where_i_want_to_go_to).getImageView().setImage(drill_image_left);
                        cell_i_will_go_to.getImageView().setScaleX(-1);
                        drillerMachine.setFuel(drillerMachine.getFuel() - 1000);
                        variable_fuel.setText(Double.toString(drillerMachine.getFuel()));
                    }
                }

            }}
        );
    }
    public static void set_all_gridpane_cells(GridPane first_gridpane, HashMap<Integer, Cell> last_hashmap,
                                              ArrayList<Obstacle> obstacle_objects, ArrayList<Ground> ground_objects,
                                              ArrayList<Soil> soil_objects){

        for (int index = 0; index<16*12; index++){
            int row_index = index / 16;
            int col_index = index % 16;
            Cell current_cell = new Cell();

            if (index < 3*16){ // The cells making the sky

                ImageView current_imageview = new ImageView();
                first_gridpane.add(current_imageview, col_index, row_index);

                current_cell.setImageView(current_imageview);
                current_cell.setImage(current_cell.getImageView().getImage());
                current_cell.setIs_ground(false);
                current_cell.setCell_index(index);
                last_hashmap.put(index, current_cell);

            } else if (index < 4*16){ // The grassy top part

                ImageView grass_imageview = new ImageView("assets/underground/top_01.png");
                first_gridpane.add(grass_imageview, col_index, row_index);

                current_cell.setImageView(grass_imageview);
                current_cell.setImage(current_cell.getImageView().getImage());
                current_cell.setIs_ground(true);
                current_cell.setCell_index(index);
                Ground curr_ground = new Ground();
                curr_ground.setKills(false);
                curr_ground.setDrillable(true);
                curr_ground.setPath("assets/underground/top_01.png");
                current_cell.setGround(curr_ground);
                last_hashmap.put(index, current_cell);


            } else {
                if (col_index == 0 || col_index == 15 || row_index == 11){ // Boulders
                    int random_index = (int)(Math.random() * obstacle_objects.size());
                    Ground current_object = obstacle_objects.get(random_index);

                    Image image = new Image(current_object.getPath());
                    ImageView imageView = new ImageView(image);
                    first_gridpane.add(imageView, col_index, row_index);

                    current_cell.setImageView(imageView);
                    current_cell.setImage(current_cell.getImageView().getImage());
                    current_cell.setIs_ground(true);
                    current_cell.setCell_index(index);
                    Ground curr_ground = new Ground();
                    curr_ground.setKills(false);
                    curr_ground.setDrillable(false);
                    curr_ground.setPath(current_object.getPath());
                    current_cell.setGround(curr_ground);
                    last_hashmap.put(index, current_cell);


                } else { // Inside part
                    if (index % 7 == 0 || index % 11 == 0){

                        int random_index = (int)(Math.random() * ground_objects.size());
                        Ground current_ground_object = ground_objects.get(random_index);

                        Image image = new Image(current_ground_object.getPath());
                        ImageView imageView = new ImageView(image);
                        first_gridpane.add(imageView, col_index, row_index);

                        current_cell.setImageView(imageView);
                        current_cell.setImage(current_cell.getImageView().getImage());
                        current_cell.setIs_ground(true);
                        current_cell.setCell_index(index);

                        Ground curr_ground = new Ground();

                        if (current_ground_object instanceof Lava){
                            curr_ground.setKills(true);
                            curr_ground.setDrillable(true);
                        } else if (current_ground_object instanceof Valuable) {
                            curr_ground.setKills(false);
                            curr_ground.setDrillable(true);
                            curr_ground.setGround_is_valuable(true);
                            set_valuable_infooo(current_ground_object);
                        } else if (current_ground_object instanceof Soil){
                            curr_ground.setKills(false);
                            curr_ground.setDrillable(true);
                        } else {
                            curr_ground.setKills(false);
                            curr_ground.setDrillable(false);
                        }
                        curr_ground.setPath(current_ground_object.getPath());
                        current_cell.setGround(curr_ground);
                        last_hashmap.put(index, current_cell);


                    } else {

                        int random_index = (int)(Math.random() * soil_objects.size());
                        Ground current_object = soil_objects.get(random_index);
                        Image image = new Image(current_object.getPath());
                        ImageView imageView = new ImageView(image);
                        first_gridpane.add(imageView, col_index, row_index);

                        current_cell.setImageView(imageView);
                        current_cell.setImage(current_cell.getImageView().getImage());
                        current_cell.setIs_ground(true);
                        current_cell.setCell_index(index);
                        Ground curr_ground = new Ground();
                        curr_ground.setKills(false);
                        curr_ground.setDrillable(true);
                        curr_ground.setPath(current_object.getPath());
                        current_cell.setGround(curr_ground);
                        last_hashmap.put(index, current_cell);
                    }
                }
            }
        }
    }
    public static Scene set_green_game_over_scene(DrillerMachine drillerMachine){
        Text game_over_text_with_money = new Text("GAME OVER!\nMoney: " + drillerMachine.getMoney());
        game_over_text_with_money.setFill(Color.WHITE);
        game_over_text_with_money.setFont(new Font(50));

        StackPane green_over_gridpane = new StackPane();
        Rectangle green_rectangle = new Rectangle(800, 600);
        green_rectangle.setFill(Color.LIMEGREEN);
        green_over_gridpane.getChildren().add(green_rectangle);
        green_over_gridpane.getChildren().add(game_over_text_with_money);
        return new Scene(green_over_gridpane, 800, 600);
    }

    public static Scene set_red_game_over_scene(){
        Text game_over_text_without_money = new Text("GAME OVER!");
        game_over_text_without_money.setFill(Color.WHITE);
        game_over_text_without_money.setFont(new Font(50));

        StackPane red_over_gridpane = new StackPane();
        Rectangle red_rectangle = new Rectangle(800, 600);
        red_rectangle.setFill(Color.DARKRED);
        red_over_gridpane.getChildren().add(red_rectangle);
        red_over_gridpane.getChildren().add(game_over_text_without_money);
        return new Scene(red_over_gridpane, 800, 600);
    }

    public static void gravity_movement(Scene scene, HashMap<Integer, Cell> last_hashmap,
                                        Image drill_image_left, Image drill_image_down, Image drill_image_up){
        scene.setOnKeyReleased(e -> {
            int current_place_index;
            for (int index = 0; index<11; index++){
                current_place_index = find_driller_index(last_hashmap, drill_image_left, drill_image_down, drill_image_up);
                int gravity_wants_to_go_here = current_place_index + 16;
                Cell cell_i_will_go_to = last_hashmap.get(gravity_wants_to_go_here);
                if (cell_i_will_go_to.getImageView().getImage() == null){
                    last_hashmap.get(current_place_index).getImageView().setImage(null);
                    last_hashmap.get(gravity_wants_to_go_here).getImageView().setImage(drill_image_up);
                }
            }
        });
    }
    public static Integer get_driller_index_from_last_hashmap(HashMap<Integer, Cell> hashMap, Image drill_image){
        for (Integer index: hashMap.keySet()){
            if (hashMap.get(index).getImageView().getImage() == drill_image){
                return index;
            }
        }
        return -1;
    }

    public static int find_driller_index(HashMap<Integer, Cell> last_hashmap, Image drill_image_left, Image drill_image_down, Image drill_image_up){
        if (get_driller_index_from_last_hashmap(last_hashmap, drill_image_left) != -1){
            return get_driller_index_from_last_hashmap(last_hashmap, drill_image_left);
        } else if (get_driller_index_from_last_hashmap(last_hashmap, drill_image_down) != -1){
            return get_driller_index_from_last_hashmap(last_hashmap, drill_image_down);
        } else if (get_driller_index_from_last_hashmap(last_hashmap, drill_image_up) != -1){
            return get_driller_index_from_last_hashmap(last_hashmap, drill_image_up);
        } else {
            return -1;
        }
    }
    public static void set_valuable_infooo(Ground current_ground_object){
        if (current_ground_object.getPath().contains("ironium")){
            ((Valuable) current_ground_object).setValuable_worth(30);
            ((Valuable) current_ground_object).setValuable_weight(10);
        } else if (current_ground_object.getPath().contains("bronzium")) {
            ((Valuable) current_ground_object).setValuable_worth(60);
            ((Valuable) current_ground_object).setValuable_weight(10);
        } else if (current_ground_object.getPath().contains("silverium")) {
            ((Valuable) current_ground_object).setValuable_worth(100);
            ((Valuable) current_ground_object).setValuable_weight(10);
        } else if (current_ground_object.getPath().contains("goldium")) {
            ((Valuable) current_ground_object).setValuable_worth(250);
            ((Valuable) current_ground_object).setValuable_weight(20);
        } else if (current_ground_object.getPath().contains("platinum")) {
            ((Valuable) current_ground_object).setValuable_worth(750);
            ((Valuable) current_ground_object).setValuable_weight(30);
        } else if (current_ground_object.getPath().contains("einsteinium")) {
            ((Valuable) current_ground_object).setValuable_worth(2000);
            ((Valuable) current_ground_object).setValuable_weight(40);
        } else if (current_ground_object.getPath().contains("emerald")) {
            ((Valuable) current_ground_object).setValuable_worth(5000);
            ((Valuable) current_ground_object).setValuable_weight(60);
        } else if (current_ground_object.getPath().contains("ruby")) {
            ((Valuable) current_ground_object).setValuable_worth(20000);
            ((Valuable) current_ground_object).setValuable_weight(80);
        } else if (current_ground_object.getPath().contains("diamond")) {
            ((Valuable) current_ground_object).setValuable_worth(100000);
            ((Valuable) current_ground_object).setValuable_weight(100);
        } else if (current_ground_object.getPath().contains("amazonite")) {
            ((Valuable) current_ground_object).setValuable_worth(500000);
            ((Valuable) current_ground_object).setValuable_weight(120);
        }
    }
}