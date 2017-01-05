(function() {
    'use strict';

    angular
        .module('kubaShopApp')
        .controller('OrderDetailsController', OrderDetailsController);

    OrderDetailsController.$inject = ['$scope', '$state', 'OrderDetails'];

    function OrderDetailsController ($scope, $state, OrderDetails) {
        var vm = this;

        vm.orderDetails = [];

        loadAll();

        function loadAll() {
            OrderDetails.query(function(result) {
                vm.orderDetails = result;
                vm.searchQuery = null;
            });
        }
    }
})();
