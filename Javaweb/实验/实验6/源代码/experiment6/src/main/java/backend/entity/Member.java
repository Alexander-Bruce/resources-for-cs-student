package backend.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Member {

    private String Mname;

    private String Mlevel;

    private Book Mbook;

    @Override
    public String toString() {
        return "会员姓名: " + this.getMname()
                + " 借阅图书: " + this.getMbook().getBname()
                + " 图书作者: " + this.getMbook().getBauthor()
                + " 图书类别: " + this.getMbook().getBcategory();
    }

    public void setBook(Book book) {
        this.Mbook = book;
    }
}
