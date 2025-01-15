import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Cell {
    private Image image;
    private ImageView imageView;
    private int cell_index;
    private boolean is_ground;

    private Ground ground;

    public void setCell_index(int cell_index) {
        this.cell_index = cell_index;
    }

    public void setGround(Ground ground) {
        this.ground = ground;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    public void setIs_ground(boolean is_ground) {
        this.is_ground = is_ground;
    }

    public Ground getGround() {
        return ground;
    }

    public Image getImage() {
        return image;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getCell_index() {
        return cell_index;
    }

    public boolean isIs_ground() {
        return is_ground;
    }
}
