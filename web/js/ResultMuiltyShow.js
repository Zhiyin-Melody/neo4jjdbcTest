$(function(){
    $('.w_ulList >li').off('click');
    $('.w_ulList >li').on('click',function(){
        var _this = $(this);
        $('.w_ulList > li').removeClass('active');
        _this.addClass('active');

        var tabIndex = _this.index();
        if( tabIndex == 1 ){
            $('.w_tableDemo').show();
            $('.w_zoomEchart').hide();
        }else{
            $('.w_tableDemo').hide();
            $('.w_zoomEchart').show();
        }
    });
});