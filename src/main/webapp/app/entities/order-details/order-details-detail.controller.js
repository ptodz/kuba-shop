(function() {
    'use strict';

    angular
        .module('kubaShopApp')
        .controller('OrderDetailsDetailController', OrderDetailsDetailController);

    OrderDetailsDetailController.$inject = ['$scope', '$rootScope', '$stateParams', 'previousState', 'entity', 'OrderDetails', 'Product', 'Orders'];

    function OrderDetailsDetailController($scope, $rootScope, $stateParams, previousState, entity, OrderDetails, Product, Orders) {
        var vm = this;

        vm.orderDetails = entity;
        vm.previousState = previousState.name;

        var unsubscribe = $rootScope.$on('kubaShopApp:orderDetailsUpdate', function(event, result) {
            vm.orderDetails = result;
        });
        $scope.$on('$destroy', unsubscribe);
    }
})();
