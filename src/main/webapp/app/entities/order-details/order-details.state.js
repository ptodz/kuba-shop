(function() {
    'use strict';

    angular
        .module('kubaShopApp')
        .config(stateConfig);

    stateConfig.$inject = ['$stateProvider'];

    function stateConfig($stateProvider) {
        $stateProvider
        .state('order-details', {
            parent: 'entity',
            url: '/order-details',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrderDetails'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-details/order-details.html',
                    controller: 'OrderDetailsController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
            }
        })
        .state('order-details-detail', {
            parent: 'entity',
            url: '/order-details/{id}',
            data: {
                authorities: ['ROLE_USER'],
                pageTitle: 'OrderDetails'
            },
            views: {
                'content@': {
                    templateUrl: 'app/entities/order-details/order-details-detail.html',
                    controller: 'OrderDetailsDetailController',
                    controllerAs: 'vm'
                }
            },
            resolve: {
                entity: ['$stateParams', 'OrderDetails', function($stateParams, OrderDetails) {
                    return OrderDetails.get({id : $stateParams.id}).$promise;
                }],
                previousState: ["$state", function ($state) {
                    var currentStateData = {
                        name: $state.current.name || 'order-details',
                        params: $state.params,
                        url: $state.href($state.current.name, $state.params)
                    };
                    return currentStateData;
                }]
            }
        })
        .state('order-details-detail.edit', {
            parent: 'order-details-detail',
            url: '/detail/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-details/order-details-dialog.html',
                    controller: 'OrderDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderDetails', function(OrderDetails) {
                            return OrderDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('^', {}, { reload: false });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-details.new', {
            parent: 'order-details',
            url: '/new',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-details/order-details-dialog.html',
                    controller: 'OrderDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: function () {
                            return {
                                quantity: null,
                                totalPrice: null,
                                id: null
                            };
                        }
                    }
                }).result.then(function() {
                    $state.go('order-details', null, { reload: 'order-details' });
                }, function() {
                    $state.go('order-details');
                });
            }]
        })
        .state('order-details.edit', {
            parent: 'order-details',
            url: '/{id}/edit',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-details/order-details-dialog.html',
                    controller: 'OrderDetailsDialogController',
                    controllerAs: 'vm',
                    backdrop: 'static',
                    size: 'lg',
                    resolve: {
                        entity: ['OrderDetails', function(OrderDetails) {
                            return OrderDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-details', null, { reload: 'order-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        })
        .state('order-details.delete', {
            parent: 'order-details',
            url: '/{id}/delete',
            data: {
                authorities: ['ROLE_USER']
            },
            onEnter: ['$stateParams', '$state', '$uibModal', function($stateParams, $state, $uibModal) {
                $uibModal.open({
                    templateUrl: 'app/entities/order-details/order-details-delete-dialog.html',
                    controller: 'OrderDetailsDeleteController',
                    controllerAs: 'vm',
                    size: 'md',
                    resolve: {
                        entity: ['OrderDetails', function(OrderDetails) {
                            return OrderDetails.get({id : $stateParams.id}).$promise;
                        }]
                    }
                }).result.then(function() {
                    $state.go('order-details', null, { reload: 'order-details' });
                }, function() {
                    $state.go('^');
                });
            }]
        });
    }

})();
