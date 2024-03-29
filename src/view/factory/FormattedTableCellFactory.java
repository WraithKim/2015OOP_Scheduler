package view.factory;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.text.TextAlignment;
import javafx.util.Callback;

import java.text.Format;

/**
 * Created by Donghwan on 12/4/2015.
 *
 * From JAVA FX Tutorial, 테이블 셀의 포멧과 텍스트 정렬 기준을 설정해주는 클래스
 */
public class FormattedTableCellFactory<S, T>
        implements Callback<TableColumn<S, T>, TableCell<S, T>> {
    private TextAlignment alignment;
    private Format format;

    @SuppressWarnings("unused")
    public TextAlignment getAlignment() {
        return alignment;
    }

    @SuppressWarnings("unused")
    public void setAlignment(TextAlignment alignment) {
        this.alignment = alignment;
    }

    @SuppressWarnings("unused")
    public Format getFormat() {
        return format;
    }

    @SuppressWarnings("unused")
    public void setFormat(Format format) {
        this.format = format;
    }

    @Override
    @SuppressWarnings("unchecked")
    public TableCell<S, T> call(TableColumn<S, T> p) {
        TableCell<S, T> cell = new TableCell<S, T>() {
            @Override
            public void updateItem(Object item, boolean empty) {
                if (item == getItem()) {
                    return;
                }
                super.updateItem((T) item, empty);
                if (item == null) {
                    super.setText(null);
                    super.setGraphic(null);
                } else if (format != null) {
                    super.setText(format.format(item));
                } else if (item instanceof Node) {
                    super.setText(null);
                    super.setGraphic((Node) item);
                } else {
                    super.setText(item.toString());
                    super.setGraphic(null);
                }
            }
        };
        cell.setTextAlignment(alignment);
        switch (alignment) {
            case CENTER:
                cell.setAlignment(Pos.CENTER);
                break;
            case RIGHT:
                cell.setAlignment(Pos.CENTER_RIGHT);
                break;
            default:
                cell.setAlignment(Pos.CENTER_LEFT);
                break;
        }
        return cell;
    }
}