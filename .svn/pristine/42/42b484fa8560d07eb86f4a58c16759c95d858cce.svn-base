define(['angular', 'angularAMD', 'angular-moment','angular-ui-router', 'ui-bootstrap','angular-animate','angular-file-upload','angular-img-crop','angular-tag','angular-ui-calendar','angular-xeditable','angular-chart','angular-post-message','angular-underscore','angular-ui-mention','jquery','angular-sanitize'], function (angular, angularAMD) {
    console.log("app start");

    // routes
    var registerRoutes = function($stateProvider,$urlRouterProvider) {

        // default
        $urlRouterProvider.otherwise("/");
        // route
        $stateProvider
            .state('root',{
                url: '',
                abstract: true,
                views: {
                    'header': angularAMD.route({
                        templateUrl: '/public/dist/js/app/template/header.tpl.html',
                        controllerUrl: "/public/dist/js/app/controllers/header.js"
                    }),
                    'footer': angularAMD.route({
                        templateUrl: '/public/dist/js/app/template/footer.tpl.html'
                    })
                }
            })
        //home
            .state("root.home", {
                url: '/',
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/home.html",
                        controllerUrl: "/public/dist/js/app/controllers/home.js"
                    })
                }
            })
        // account
            .state("root.account", angularAMD.route({
                url: "/account",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/account.html",
                        controllerUrl: "/public/dist/js/app/controllers/account.js"
                    })
                }
            }))
            //sale
            .state("root.match", angularAMD.route({
                url: "/match",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/match.html",
                        controllerUrl: "/public/dist/js/app/controllers/match.js"
                    })
                }
            }))
            //sale
            .state("root.suggestion", angularAMD.route({
                url: "/suggestion",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/suggestion.html",
                        controllerUrl: "/public/dist/js/app/controllers/suggestion.js"
                    })
                }
            }))
            //sale
            .state("root.sales", angularAMD.route({
                url: "/sales",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/sales.html",
                        controllerUrl: "/public/dist/js/app/controllers/sales.js"
                    })
                }
            }))
            // email
            .state("root.email", angularAMD.route({
                url: "/email",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/email.html",
                        controllerUrl: "/public/dist/js/app/controllers/email.js"
                    })
                }
            }))
            // chat
            .state("root.chat", angularAMD.route({
                url: "/chat",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/chat.html",
                        controllerUrl: "/public/dist/js/app/controllers/chat.js"
                    })
                }
            }))
            // contact
            .state("root.contact", angularAMD.route({
                url: "/contact",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/contact.html",
                        controllerUrl: "/public/dist/js/app/controllers/contact.js"
                    })
                }
            }))
            // board
            .state("root.board", angularAMD.route({
                url: "/board?id",
                views: {

                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/board.html",
                        controllerUrl: "/public/dist/js/app/controllers/board.js"
                    })
                },
                params: {
                    id: null
                }
            }))
            // board
            .state("root.disk", angularAMD.route({
                url: "/disk?id&root&path",
                views: {

                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/disk.html",
                        controllerUrl: "/public/dist/js/app/controllers/disk.js"
                    })
                },
                params: {
                    id: null,
                    root:null,
                    path:null
                }
            }))
        // profile
            .state("root.profile", angularAMD.route({
                url: "/profile",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/profile.html",
                        controllerUrl: "/public/dist/js/app/controllers/profile.js"
                    })
                }
            }))
            // profile
            .state("root.team", angularAMD.route({
                url: "/team",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/team.html",
                        controllerUrl: "/public/dist/js/app/controllers/team.js"
                    })
                }
            }))
	        //task
	        .state("root.task", angularAMD.route({
	            url: "/task",
	            views: {
	                'container@' :angularAMD.route({
	                    templateUrl: "/public/dist/js/app/template/task.html",
	                    controllerUrl: "/public/dist/js/app/controllers/task.js"
	                })
	            }
	        }))
            //calendar
            .state("root.calendar", angularAMD.route({
                url: "/calendar",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/calendar.html",
                        controllerUrl: "/public/dist/js/app/controllers/calendar.js"
                    })
                }
            }))
            //dashboard
            .state("root.dashboard", angularAMD.route({
                url: "/dashboard",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/dashboard.html",
                        controllerUrl: "/public/dist/js/app/controllers/dashboard.js"
                    })
                }
            }))
            //report
            .state("root.report", angularAMD.route({
                url: "/report",
                views: {
                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/report.html",
                    })
                }
            }))
            .state("root.report.primary",angularAMD.route({
                url: '/primary',
                templateUrl: '/public/dist/js/app/template/report-primary.html',
                controllerUrl: '/public/dist/js/app/controllers/report.js'
            }))
            //report
            .state("root.report.new", angularAMD.route({
                url: "/new",
                templateUrl: "/public/dist/js/app/template/report-new.html",
                controllerUrl: "/public/dist/js/app/controllers/report-new.js"
            }))
            //report
            .state("root.report.list", angularAMD.route({
                url: "/list",
                templateUrl: "/public/dist/js/app/template/report-list.html",
                controllerUrl: "/public/dist/js/app/controllers/report-list.js"

            }))
            //report add by zj 2016/11/28
            .state("root.report.template",angularAMD.route({
                url: "/template",
                templateUrl: "/public/dist/js/app/template/report-template.html",
                controllerUrl: "/public/dist/js/app/controllers/report-template.js"
            }))
            // report
            .state("root.report.detail", angularAMD.route({
                url: "/detail?id",
                templateUrl: "/public/dist/js/app/template/report-detail.html",
                controllerUrl: "/public/dist/js/app/controllers/report-detail.js",
                params: {
                    id: null
                }
            }))
            // board
            .state("root.account-board", angularAMD.route({
                url: "/account-board?id",
                views: {

                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/account-detail.html",
                        controllerUrl: "/public/dist/js/app/controllers/account-detail.js"
                    })
                },
                params: {
                    id: null
                }
            }))
            // board
            .state("root.contact-board", angularAMD.route({
                url: "/contact-board?id",
                views: {

                    'container@' :angularAMD.route({
                        templateUrl: "/public/dist/js/app/template/contact-detail.html",
                        controllerUrl: "/public/dist/js/app/controllers/contact-detail.js"
                    })
                },
                params: {
                    id: null
                }
            }));
    };

    var app = angular.module("smartCrmApp", ['angularMoment','ui.router','ui.bootstrap','ngAnimate','angularFileUpload','ngImgCrop','ngTagsInput','ui.calendar','xeditable','chart.js','ngPostMessage','underscore','ui.mention','ngSanitize']);

    app.config(["$stateProvider", "$urlRouterProvider", registerRoutes]);
    //add by zj 16/11/09
    app.config(["$compileProvider",function($compileProvider){
        $compileProvider.aHrefSanitizationWhitelist(/^\s*(data):/);
    }]);
    app.run(['$state', '$rootScope', function($state, $rootScope) {
        $rootScope.$on('$stateChangeStart', function(e, toState, toParams, fromState, fromParams) {

            if(toState.name.indexOf('report') > 0) {
                $rootScope.hasSide = true;
            } else {
                $rootScope.hasSide = false;
            }
            //console.log(toState);
            //console.log(fromState);
        });
    }]);
    return angularAMD.bootstrap(app);
});