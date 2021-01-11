angular.module('market', []).controller('indexController', function ($scope, $http) {
    const contextPath = 'http://localhost:8189/market';

//     $scope.fillTable = function () {
//         $http.get(contextPath + '/products')
//             .then(function (response) {
//                 console.log(response);
//                 $scope.ProductsList = response.data;
//             });
//     };

    $scope.fillTable = function () {
        $http({
            url: contextPath + '/products',
            method: 'GET',
            params: {
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null
            }
        }).then(function (response) {
            $scope.ProductsList = response.data;
        });
    };

    $scope.submitCreateNewProduct = function () {
        $http.post(contextPath + '/products', $scope.newProduct)
            .then(function (response) {
                $scope.newProduct = null;
                $scope.fillTable();
            });
    };

 $scope.deleteById = function (id) {
            $http.get(contextPath + '/products/delete/' + id)
                .then(function (response) {
                 console.log(response);
                    $scope.fillTable();
                });
        };

    $scope.fillTable();
});