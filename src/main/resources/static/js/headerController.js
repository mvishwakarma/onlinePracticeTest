/*(function(){*/
		function fetchHeaderMenus(){
			var xhttp = new XMLHttpRequest();
			xhttp.onreadystatechange = function() {
				if (this.readyState == 4 && this.status == 200) {
					if(this.responseText){
						sessionStorage.setItem('_menu_data', this.responseText);
						setHeaderMenus(JSON.parse(this.responseText)); 
					} 
				}
			};
			xhttp.open("GET", location.origin+"/api/menu/practiceTest", true);
			xhttp.setRequestHeader("Content-type", "application/json");
			xhttp.send("JSON Data Response.");
		}
		
		var localMenuData = sessionStorage.getItem('_menu_data');
		
		if(!localMenuData){
			console.log('Not exist');
			fetchHeaderMenus();
		}else{
			console.log('exist');
			setHeaderMenus(JSON.parse(localMenuData));
		}
		
		
		function setHeaderMenus(data){
			if(data.length){
				var final = {root:{}, subroot:[]}; 
				for(var i=0; i<data.length; i++){
					if(!data[i].parentId){
						final['root'] = {'name':data[i].name,'id':data[i].id};
						continue;
					}
					else if(data[i].parentId && data[i].parentId === final['root']['id']){
						final['subroot'].push({'name':data[i], 'submenu':[]});
						continue;
					}
					if(final.subroot.length){
						final.subroot.forEach(function(_o,ind){
							if(data[i].parentId ==_o.name.id) final['subroot'][ind]['submenu'].push(data[i]);
						})
					}
					
				}  
				var html = '<a class="dropdown-toggle" data-toggle="dropdown"  href="javascript:void(0)" >';
					html+= final.root.name+'</a><ul class="dropdown-menu">';
				final.subroot.forEach(function(o){
					html+= '<li class="menu-item dropdown dropdown-submenu"><a href="javascript:void(0)">'+o.name.name+'</a>';
					if(o.submenu.length){
						html+='<ul class="dropdown-menu">'
						o.submenu.forEach(function(_p){
							html+='<li><a href="javascript:void(0)" onclick="showQuizes('+"'"+_p.quizTags+"'"+')">'+_p.name+'</a></li>'
						});
						html+='</ul>'
					}
				})
				html+='</ul>'
				document.getElementById('practiceTestMenu').innerHTML = html;
			}
		};
		
		function showQuizes(qTag){ 
			localStorage.setItem('qTag',qTag);
            location.pathname = '/quizLists';
		}
/*})();*/