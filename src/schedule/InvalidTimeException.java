package schedule;

/**
 * Created by Donghwan on 11/29/2015.
 *
 * 맞지 않는 시간에 대한 예외
 */
class InvalidTimeException extends Exception{
    @Override
    public String getMessage() {
        return "Invalid time";
    }
}
