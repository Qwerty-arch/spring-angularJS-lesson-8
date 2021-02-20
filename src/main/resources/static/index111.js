angular.module('market', ['ngStorage']).controller('indexController', function ($scope, $http, $localStorage) {
    const contextPath = 'http://localhost:8189/market';
    $scope.authorized = false;
    $scope.registration = false;

    $scope.showProductsPage = function (pageIndex = 1) {
        $http({
            url: contextPath + '/api/v1/products',
            method: 'GET',
            params: {
                title: $scope.filter ? $scope.filter.title : null,
                min_price: $scope.filter ? $scope.filter.min_price : null,
                max_price: $scope.filter ? $scope.filter.max_price : null,
                p: pageIndex
            }
        }).then(function (response) {
            $scope.ProductsPage = response.data;

            let minPageIndex = pageIndex - 2;
            if (minPageIndex < 1) {
                minPageIndex = 1;
            }

            let maxPageIndex = pageIndex + 2;
            if (maxPageIndex > $scope.ProductsPage.totalPages) {
                maxPageIndex = $scope.ProductsPage.totalPages;
            }

            $scope.PaginationArray = $scope.generatePagesIndexes(minPageIndex, maxPageIndex);
        });
    };

    $scope.showCart = function () {
        $http({
            url: contextPath + '/api/v1/cart',
            method: 'GET'
        }).then(function (response) {
            $scope.cartVisible;
            $scope.Cart = response.data;
        });
    };

    $scope.showMyOrders = function () {
                $http({
                    url: contextPath + '/api/v1/orders',
                    method: 'GET'
                }).then(function (response) {
                    $scope.MyOrders = response.data;
                });
            };

    $scope.generatePagesIndexes = function(startPage, endPage) {
        let arr = [];
        for (let i = startPage; i < endPage + 1; i++) {
            arr.push(i);
        }
        return arr;
    }

//    $scope.submitCreateNewProduct = function () {
//        $http.post(contextPath + '/products', $scope.newProduct)
//            .then(function (response) {
//                $scope.newProduct = null;
//                $scope.fillTable();
//            });
//    };

    $scope.deleteProductById = function (productId) {
        $http.delete(contextPath + '/api/v1/products/' + productId)
            .then(function (response) {
                $scope.showProductsPage();
            });
    }

    $scope.addToCart = function (productId) {
        $http.get(contextPath + '/api/v1/cart/add/' + productId)
            .then(function (response) {
                $scope.cartVisible = true;
                $scope.showCart();
            });
    }

    $scope.clearCart = function () {
        $http.get(contextPath + '/api/v1/cart/clear')
            .then(function (response) {
                $scope.cartVisible = false;
                $scope.showCart();
            });
    }



    $scope.tryToAuth = function () {
            $http.post(contextPath + '/auth', $scope.user)
                .then(function successCallback(response) {
                    if (response.data.token) {
                        $http.defaults.headers.common.Authorization = 'Bearer ' + response.data.token;

//                         $localStorage.happyUsername = $scope.user.username;
//                         $localStorage.happyTokenWithBearerPrefix = 'Bearer ' + response.data.token;

                        $scope.user.username = null;
                        $scope.user.password = null;
                        $scope.authorized = true;
                        $scope.showProductsPage();
                        $scope.showMyOrders();
                        $scope.showCart();
                    }
                }, function errorCallback(response) {
                    window.alert("Error");
                });
        };

        $scope.tryToRegistration = function () {
                    $http.post(contextPath + '/auth/registration', $scope.user)
                        .then(function successCallback(response) {
                                $scope.registration = false;
                        }, function errorCallback(response) {
                            window.alert("Error");
                        });
                };

        $scope.ButtonRegistration = function () {
                         $scope.registration = true;
                     }



//     $scope.logout = function () {
//                 $http.defaults.headers.common.Authorization = null;
//                 delete $localStorage.happyUsername;
//                 delete $localStorage.happyTokenWithBearerPrefix;
//                 $scope.authorized = false;
//             }

    // TODO в будущем POST, а не GET
    $scope.createOrder = function () {
                $http.get(contextPath + '/api/v1/orders/create')
                    .then(function (response) {
                        $scope.showMyOrders();
                        $scope.showCart();
                    });
            }

//    if ($localStorage.happyUsername) {
//                $http.defaults.headers.common.Authorization = $localStorage.happyTokenWithBearerPrefix;
//                $scope.showProductsPage();
//                $scope.showMyOrders();
//                $scope.showCart();
//                $scope.authorized = true;
//            }

});