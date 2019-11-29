layui.use(['form','layer','laydate','table','laytpl'],function(){
    var form = layui.form,
        layer = parent.layer === undefined ? layui.layer : top.layer,
        $ = layui.jquery,
        laydate = layui.laydate,
        laytpl = layui.laytpl,
        table = layui.table;

    //新闻列表
    var tableIns = table.render({
        elem: '#newsList',
        url : '/queryMenu',
        cellMinWidth : 95,
        page : true,
        height : "full-125",
        limit : 20,
        limits : [10,15,20,25],
       /* id : "newsListTable",*/
        cols : [[
            {type: "checkbox", fixed:"left", width:80},
            {field: 'id', title: 'ID', width:200, align:"center"},
            {field: 'title', title: '试题名称', width:180},
            {field: 'author', title: '发布者',width:200, align:'center', templet:function(d){
                    return d.author.uname;
                }},
            {field: 'ispublic', title: '发布状态', width:180, align:'center', templet:function(d){
                    var str="私密浏览";
                    if(d==1){
                        str="公开浏览"
                    }

                    return str;
                }},

            {field: 'istop', title: '是否置顶',width:120, align:'center', templet:function(d){

                    if(d.istop==1){
                        return '<input type="checkbox"  lay-filter="newsTop" lay-skin="switch" lay-text="是|否" checked>'
                    }else {
                        return '<input type="checkbox"  lay-filter="newsTop" lay-skin="switch" lay-text="是|否">'
                    }
                }},
            {field: 'opentime', title: '发布时间', align:'center', minWidth:110, templet:function(d){
                    var date=new Date(d.opentime);
                    return date.getFullYear()+"-"+(date.getMonth()+1)+"-"+date.getDate()+" "+date.getHours()+":"+date.getMinutes();
                }},
            {title: '操作', width:230, templet:'#newsListBar',fixed:"right",align:"center"}
        ]]
    });

    //是否置顶
    table.on('row', function(obj){
        var data = obj.data;
        var index = layer.msg('修改中，请稍候',{icon: 16,time:false,shade:0.8});
        setTimeout(function(){
            layer.close(index);
            $.ajax({
                url:"/updateIsTop/"+data.id+"/"+data.istop,
                success:function (data){
                    if(data=="ok"){
                        layer.msg("修改成功！");
                    }else{
                        layer.msg("修改失败！");
                    }
                }
            })
        },500);
    })

    $(".search_btn").on("click",function(){
        if($(".searchVal").val() != ''){
            table.reload("newsListTable",{
                page: {
                    curr: 1
                },
                where: {
                    key: $(".searchVal").val() 
                }
            })
        }else{
            layer.msg("请输入搜索的内容");
        }
    });

    //添加试题
    function addNews(edit){
        var index = layui.layer.open({
            title : "添加试题",
            type : 2,
            content : "examAdd.html",
            success : function(layero, index){
                var body = layui.layer.getChildFrame('body', index);
                if(edit){
                    body.find(".newsName").val(edit.newsName);
                    body.find(".abstract").val(edit.abstract);
                    body.find(".thumbImg").attr("src",edit.newsImg);
                    body.find("#news_content").val(edit.content);
                    body.find(".newsStatus select").val(edit.newsStatus);
                    body.find(".openness input[name='openness'][title='"+edit.newsLook+"']").prop("checked","checked");
                    body.find(".newsTop input[name='newsTop']").prop("checked",edit.newsTop);
                    form.render();
                }
                setTimeout(function(){
                    layui.layer.tips('点击此处返回试题列表', '.layui-layer-setwin .layui-layer-close', {
                        tips: 3
                    });
                },500)
            }
        })
        layui.layer.full(index);

        $(window).on("resize",function(){
            layui.layer.full(index);
        })
    }
    $(".addNews_btn").click(function(){
        addNews();
    })

    //批量删除
    $(".delAll_btn").click(function(){
        var checkStatus = table.checkStatus('newsListTable'),
            data = checkStatus.data,
            newsId = [];
        if(data.length > 0) {
            for (var i in data) {
                newsId.push(data[i].newsId);
            }
            layer.confirm('确定删除选中的文章？', {icon: 3, title: '提示信息'}, function (index) {
                // $.get("删除文章接口",{
                //     newsId : newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            })
        }else{
            layer.msg("请选择需要删除的文章");
        }
    })

    //列表操作
    table.on('tool(newsList)', function(obj){
        var layEvent = obj.event,
            data = obj.data;

        if(layEvent === 'edit'){ //编辑
            addNews(data);
        } else if(layEvent === 'del'){ //删除
            layer.confirm('确定删除此文章？',{icon:3, title:'提示信息'},function(index){
                // $.get("删除文章接口",{
                //     newsId : data.newsId  //将需要删除的newsId作为参数传入
                // },function(data){
                tableIns.reload();
                layer.close(index);
                // })
            });
        } else if(layEvent === 'look'){ //预览
            layer.alert("此功能需要前台展示，实际开发中传入对应的必要参数进行文章内容页面访问")
        }
    });

})