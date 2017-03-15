require.config({
    paths: {
        "jquery": "/public/plugins/jQuery/jQuery-2.2.0.min",
        "jquery-ui": "/public/plugins/jQueryUI/jquery-ui.min",
        "ui-bootstrap" : "/public/plugins/angularjs/ui-bootstrap-tpls-1.3.0.min",
        "angular" :"/public/plugins/angularjs/angular.min",
        "angular-animate":"/public/plugins/angularjs/angular-animate.min",
        "angular-ui-router" :"/public/plugins/angularjs/angular-ui-router.min",
        "angular-file-upload" :"/public/plugins/angularjs/angular-file-upload.min",
        "angular-post-message" :"/public/plugins/angularjs/angular-post-message",
        "angular-tag" :"/public/plugins/angularjs/ng-tags-input.min",
        "angular-img-crop" :"/public/plugins/angularjs/ng-img-crop",
        "angular-xeditable" :"/public/plugins/angularjs/xeditable.min",
        "angularAMD" :"/public/plugins/angularjs/angularAMD.min",
        "ngload" :"/public/plugins/angularjs/ngload.min",
        "moment" :"/public/plugins/moment/moment.min",
        "angular-moment" :"/public/plugins/angularjs/angular-moment.min",
        "sockjs" :"/public/plugins/websocket/sockjs.min",
        "stomp" :"/public/plugins/websocket/stomp.min",
        "angular-ui-calendar":"/public/plugins/angularjs/angular-ui-calendar",
        "fullcalendar":"/public/plugins/fullcalendar/fullcalendar.min",
        "angular-dragdrop":"/public/plugins/angularjs/angular-dragdrop",
        "angular-chart":"/public/plugins/angularjs/angular-chart",
        "chart":"/public/plugins/chartjs/Chart.min",
        "app" : "/public/dist/js/start-customer"
    },
    shim: {
        'angular': { exports: "angular" },
        'angular-animate':['angular'],
        'ui-bootstrap' :['angular'],
        'angular-xeditable' :['angular'],
        'angular-ui-router': ['angular'],
        'angular-file-upload': ['angular'],
        'angular-post-message': ['angular'],
        'angular-img-crop': ['angular'],
        'angular-tag': ['angular'],
        'angularAMD' :['angular'],
        'ngload' : ['angularAMD'],
        'moment':['jquery'],
        'jquery-ui':['jquery'],
        'angular-moment' :['moment','angular'],
        'sockjs' : {
            exports : "SockJS"
        },
        'stomp' : {
            exports : "Stomp"
        },
        'fullcalendar':['moment'],
        'angular-ui-calendar':['angular','fullcalendar'],
        'angular-dragdrop': ['angular','jquery-ui'],
        'angular-chart': ['angular','chart']
    },
    deps:['app']
});