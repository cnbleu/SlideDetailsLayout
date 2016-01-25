package cn.bleu.slidedetailsdemo;

/**
 * <b>Project:</b> SlideDetailsLayout<br>
 * <b>Create Date:</b> 16/1/25<br>
 * <b>Author:</b> Gordon<br>
 * <b>Description:</b> <br>
 */
public interface ISlideCallback {

    void openDetails(boolean smooth);

    void closeDetails(boolean smooth);
}
