(function(angular){
    'use strict';
        
    angular
    .module('OpenDashboard')
    .controller('SelectCardController', function($log, $scope, $state,$translate, $translatePartialLoader, Notification,
                                            _ , registry, ContextMappingService,
                                            contextMapping, dashboardId) {
    	$translatePartialLoader.addPart('card');
        $translate.refresh();
        $scope.$parent.contextMapping = contextMapping;
        $scope.$parent.activeDashboard = _.find($scope.$parent.contextMapping.dashboards,{'id':dashboardId});
        $scope.cards = registry.registry;
        
        $scope.addCard = function(cardType) {
          $log.log('add card type: '+cardType);
          
          var card = angular.copy($scope.cards[cardType]);
          $log.debug(card);
          
          var cardConfig = card.config;
          if (cardConfig && cardConfig.length > 0) {
            $state.go('index.addCard',{cmid:$scope.$parent.contextMapping.id,dbid:$scope.$parent.activeDashboard.id,cardType:cardType});
          }
          else {
        	card.config = {};
            ContextMappingService
              .addCard($scope.$parent.contextMapping, $scope.$parent.activeDashboard, card)
              .then(
                  function (updatedContextMapping) {
                      $state.go('index.dashboard',{cmid:$scope.$parent.contextMapping.id,dbid:$scope.$parent.activeDashboard.id});
                  },
                  function (error) {
                      $log.error(error);
                      Notification.error('Unable to add card.');
                  });
          }
        };
    });

    angular
    .module('OpenDashboard')
    .controller('AddCardController', function($log, $scope, $state, $translate, $translatePartialLoader, Notification,
                                            _ , registry, ContextMappingService,
                                            contextMapping, dashboardId, card) {
    	$translatePartialLoader.addPart('card');
        $translate.refresh();
        $scope.$parent.contextMapping = contextMapping;
        $scope.$parent.activeDashboard = _.find($scope.$parent.contextMapping.dashboards,{'id':dashboardId});
        $scope.newConfig = {};
        $scope.card = card;
        
        $scope.cancel = function() {
            $state.go('index.selectCard', {cmid:$scope.$parent.contextMapping.id,dbid:$scope.$parent.activeDashboard.id});
        };
        
        $scope.addCard = function() {
            $scope.card.config = $scope.newConfig;
            ContextMappingService
            .addCard($scope.$parent.contextMapping, $scope.$parent.activeDashboard, $scope.card)
            .then(
                function (updatedContextMapping) {
                    $state.go('index.dashboard',{cmid:$scope.$parent.contextMapping.id,dbid:$scope.$parent.activeDashboard.id});
                },
                function (error) {
                    $log.error(error);
                    Notification.error('Unable to add card.');
                });
        };
        
    });
    
    angular
    .module('OpenDashboard')
    .controller('EditCardController', function($log, $scope, $state, $translate, $translatePartialLoader, Notification,
                                            _ , registry, ContextMappingService,
                                            contextMapping, dashboardId, cardId) {
    	$translatePartialLoader.addPart('card');
        $translate.refresh();
        $scope.$parent.contextMapping = contextMapping;
        $scope.$parent.activeDashboard = _.find($scope.$parent.contextMapping.dashboards,{'id':dashboardId});
        $scope.card = _.find($scope.$parent.activeDashboard.cards,{'id':cardId});
        $scope.cardConfig = angular.copy(registry.registry[$scope.card.cardType].config);
        
        $scope.cancel = function() {
        	$state.go('index.dashboard',{cmid:$scope.$parent.contextMapping.id,dbid:$scope.$parent.activeDashboard.id});
        };
        
        $scope.editCard = function() {
            ContextMappingService
            .update($scope.$parent.contextMapping)
            .then(
                function (updatedContextMapping) {
                	$state.go('index.dashboard',{cmid:$scope.$parent.contextMapping.id,dbid:$scope.$parent.activeDashboard.id});
                },
                function (error) {
                    $log.error(error);
                    Notification.error('Unable to configure card.');
                });
        };
        
    });

    
    angular
    .module('OpenDashboard')
    .controller('RemoveCardController', function($log, $scope, $state, $translate, $translatePartialLoader, Notification,
                                            _ , ContextMappingService,
                                            contextMapping, dashboardId, cardId) {
    	$translatePartialLoader.addPart('card');
        $translate.refresh();
        $scope.$parent.contextMapping = contextMapping;
        $scope.$parent.activeDashboard = _.find($scope.$parent.contextMapping.dashboards,{'id':dashboardId});
        $scope.card = _.find($scope.$parent.activeDashboard.cards,{'id':cardId});
        $scope.cancel = function() {
        	$state.go('index.dashboard',{cmid:$scope.$parent.contextMapping.id,dbid:$scope.$parent.activeDashboard.id});
        };
        
        $scope.removeCard = function() {
        	ContextMappingService
            .removeCard($scope.$parent.contextMapping, $scope.$parent.activeDashboard, $scope.card)
            .then(
                function(updatedContextMapping) {
                	$state.go('index.dashboard',{cmid:$scope.$parent.contextMapping.id,dbid:$scope.$parent.activeDashboard.id});
                },
                function (error) {
                    $log.error(error);
                    Notification.error('Unable to remove card.');
                }
            );
        }
        
        
    });

})(angular);