define(['../services/ReportService.js'],function(){
    return ['$scope', 'ReportService', function($scope,ReportService){
 //定义页面对象
                $scope.report = {
                    types: [{tag:"Contact",value:"contact"},{tag:"Sales",value:"sales"},{tag:"Account",value:"account"}]
                };

                $scope.filters = [];

                $scope.joins = ["And","Or"];

                $scope.fields = {
                    contact: [
                        {name:"Recent view", operatorType:"compare",   valueType:"text"},
                        {name:"Gender",      operatorType:"is",        valueType:"gender"},
                        {name:"Stars",       operatorType:"includes",  valueType:"text"},
                        {name:"Connected",   operatorType:"compare",    valueType:"date"}
                    ],
                    sales: [
                        {name:"States", operatorType:"is", valueType:"text"},
                        {name:"Shared", operatorType:"compare", valueType:"text"}
                    ],
                    account: [
                        {name:"FirstName", operatorType:"is", valueType: "text"},
                        {name:"LastName", operatorType:"is", valueType: "text" }
                    ]
                }

                $scope.operators = {
                    compare: ["less than", "less than or equal to", "equal", "great than or equal to", "great than"],
                    is: ["is", "is not"],
                    includes: ["includes", "not includes"]
                };

                $scope.values = {
                    gender: ["Male", "Female"]
                };


                //初始化一行filter
                $scope.initFilter = function(){
                    var type = $scope.report.type;
                    var len = $scope.report.types.length;

                    //查询该type下的第一个field、operator和value。并设置joins默认为And
                    var field = eval('$scope.fields.'+type+'[0].name');
                    var operatorType = eval('$scope.fields.'+type+'[0].operatorType');
                    var valueType = eval('$scope.fields.'+type+'[0].valueType');
                    if(valueType == "gender")
                        value = eval('$scope.values.'+valueType+'[0]');
                    else
                        value = null;
                    var operator = eval('$scope.operators.'+operatorType+'[0]');
                    var join = $scope.joins[0];

                    var filter = {join:join, field:field, operatorType:operatorType, operator:operator, valueType:valueType, value:value};
                    return filter;
                };

                //增加一行Filter
                $scope.addFilter = function(){
                    var filter = $scope.initFilter();
                    $scope.filters.push(filter);
                };

                //删除一行Filter
                $scope.removeFilter = function(index){
                    $scope.filters.splice(index,1);
                }

                //翻转按钮
                $scope.turn = function(state,type,index){
                    //reportType的翻转
                    if(type=='report'){
                        var report = $scope.report.types;
                        var type = $scope.report.type;
                        var len = report.length;
                        var ind;
                        for(var i=0; i<len; i++){
                            if(type == report[i].value){
                                ind = i;
                                break;
                            }
                        }
                        //这里定义一个操作ind的函数;
                        if(state=='up'){
                            ind = (ind+1)%len;
                        }
                        else if(state=='down'){
                            ind--;
                            if(ind < 0) ind = len-1;
                        }
                        $scope.report.type = $scope.report.types[ind].value;
                        $scope.filters = [];
                        $scope.filters.push($scope.initFilter());
                    }
                    //对join的翻转
                    else if(type=='join'){
                        var ind = $scope.joins.indexOf($scope.filters[index].join);
                        var len = $scope.joins.length;
                        if(state=='up'){
                            ind = (ind+1)%len;
                        }
                        else if(state=='down'){
                            ind--;
                            if(ind<0) ind = len-1;
                        }
                        $scope.filters[index].join = $scope.joins[ind];
                    }
                    //对field的翻转
                    else if(type=='field'){
                        var reportType = $scope.report.type;
                        var fields = eval('$scope.fields.'+reportType);
                        var len = fields.length;
                        var field = $scope.filters[index].field;
                        //
                        for(var i=0;i<len;i++){
                            if(field == fields[i].name){
                                ind = i;
                                break;
                            }
                        }
                        if(state=='up'){
                            ind = (ind+1)%len;
                        }
                        else if(state=='down'){
                            ind--;
                            if(ind<0) ind = len-1;
                        }
                        $scope.filters[index].field = fields[ind].name;

                        //设置operator、value
                        $scope.setOperatorAndValue(index);
                    }
                    //对operator的翻转
                    else if(type=='operator'){
                        var reportType = $scope.report.type;
                        var operatorType = $scope.filters[index].operatorType;
                        var operator = $scope.filters[index].operator;
                        var operators = eval('$scope.operators.'+operatorType);
                        var len = operators.length;
                        for(var i=0; i<len; i++){
                            if(operator == operators[i]){
                                ind = i;
                                break;
                            }
                        }
                        if(state=='up'){
                            ind = (ind+1)%len;
                        }
                        else if(state=='down'){
                            ind--;
                            if(ind<0) ind = len-1;
                        }
                        $scope.filters[index].operator = operators[ind];
                    }
                };

                //根据filter的name，设定operator和value
                $scope.setOperatorAndValue = function(index){
                    var type = $scope.report.type;
                    var field = $scope.filters[index].field;

                    var fields = eval('$scope.fields.'+type);
                    var len = fields.length;
                    for(var i=0; i<len; i++){
                        //查询filter中的field在该reportType下的fields中的index；
                        if(field == fields[i].name){
                            ind = i;
                            break;
                        }
                    }
                    //查询该field对应的operatorType,valueType;
                    var operatorType = eval('$scope.fields.'+type+'['+ind+'].operatorType');
                    var valueType = eval('$scope.fields.'+type+'['+ind+'].valueType');
                    if(valueType == "gender"){
                        value = eval('$scope.values.'+valueType+'[0]');
                    }
                    else{
                        value = null;
                    }

                    //查询该operatorType下的第一个值
                    var operator = eval('$scope.operators.'+operatorType+'[0]');

                    //更新filter
                    $scope.filters[index].operator = operator;
                    $scope.filters[index].operatorType = operatorType;
                    $scope.filters[index].value = value;
                    $scope.filters[index].valueType = valueType;
                };

                //初始化页面
                $scope.report.type = $scope.report.types[0].value;
                $scope.filters = [];
                $scope.filters.push($scope.initFilter());


    }];
});