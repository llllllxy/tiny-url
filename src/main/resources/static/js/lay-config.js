/**
 * date:2019/08/16
 * author:Mr.Chung
 * description:此处放layui自定义扩展
 */
window.rootPath = (function (src) {
    src = document.scripts[document.scripts.length - 1].src;
    return src.substring(0, src.lastIndexOf("/") + 1);
})();

layui.config({
    base: window.rootPath + "lay-module/",
    version: true
}).extend({
    miniAdmin: "layuimini/miniAdmin", // layuimini后台扩展
    miniMenu: "layuimini/miniMenu", // layuimini菜单扩展
    miniPage: "layuimini/miniPage", // layuimini 单页扩展
    miniTheme: "layuimini/miniTheme", // layuimini 主题扩展
    step: 'step-lay/step', // 分步表单扩展
    treeTableLay: 'treeTableLay/treeTableLay', //table树形扩展
    tableSelect: 'tableSelect/tableSelect', // table选择扩展
    iconPickerFa: 'iconPicker/iconPickerFa', // fa图标选择扩展
    echarts: 'echarts/echarts', // echarts图表扩展
    echartsTheme: 'echarts/echartsTheme', // echarts图表主题扩展
    wangEditor: 'wangEditor/wangEditor', // wangEditor富文本扩展
    layarea: 'layarea/layarea', //  省市县区三级联动下拉选择器
    layCascader: 'cascader/cascader', // 级联选择器
});