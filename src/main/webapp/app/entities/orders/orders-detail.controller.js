(function() {
    'use strict';

    angular
        .module('kubaShopApp')
        .controller('OrdersDetailController', OrdersDetailController);

    OrdersDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'Orders', 'OrderDetails', 'Customer'];

    function OrdersDetailController($scope, $rootScope, $stateParams, previousState, entity, Orders, OrderDetails, Customer) {
        var vm = this;

        vm.orders = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kubaShopApp:ordersUpdate', function(event, result) {
            vm.orders = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
