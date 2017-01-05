(function() {
    'use strict';

    angular
        .module('kubaShopApp')
        .controller('OrderDetailsDialogController', OrderDetailsDialogController);

    OrderDetailsDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', '$q', 'entity', 'OrderDetails', 'Product', 'Orders'];

    function OrderDetailsDialogController ($timeout, $scope, $stateParams, $uibModalInstance, $q, entity, OrderDetails, Product, Orders) {
        var vm = this;

        vm.orderDetails = entity;
        vm.clear = clear;
        vm.save = save;
        vm.products = Product.query({filter: 'orderdetails-is-null'});
        $q.all([vm.orderDetails.$promise, vm.products.$promise]).then(function() {
            if (!vm.orderDetails.productId) {
                return $q.reject();
            }
            return Product.get({id : vm.orderDetails.productId}).$promise;
        }).then(function(product) {
            vm.products.push(product);
        });
        vm.orders = Orders.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.orderDetails.id !== null) {
                OrderDetails.update(vm.orderDetails, onSaveSuccess, onSaveError);
            } else {
                OrderDetails.save(vm.orderDetails, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('kubaShopApp:orderDetailsUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
