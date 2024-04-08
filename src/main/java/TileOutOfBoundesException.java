/** Исключение выхода за пределы поля игры */
public class TileOutOfBoundesException extends Exception{
    public TileOutOfBoundesException(String name) {
        super(name);
    }
}
