angular.module('bqTestModule', [])
    .controller('FetchController', ['$scope', '$http',
        function ($scope, $http) {
            $scope.selectedState ="";
            $scope.membersPerState = null;
            $scope.handleSelection = () => {
                $scope.membersPerState = $scope.members[$scope.selectedState];
            }

            fetchData();

            function fetchData() {
                $http({method: 'GET', url: 'api/load-data'}).then(function (response) {
                    const resObj = response.data;
                    $scope.states = Object.keys(resObj);
                    $scope.members = resObj;
                }, function (reason) {
                    console.log('error ' + reason)
                });
            }
        }]);
