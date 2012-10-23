# ring.velocity

A Clojure library designed to render velocity template for ring/compojure in clojure.

## Usage

 Adds dependency in leiningen project.clj:
     
      [ring.velocity "0.1.2"]
	  
 Create a directory named `templates` in your project directory to keep all velocity templates.
 
 Create a template `templates/test.vm`:
   
      hello,$name,your age is $age.
	 
 Use ring.velocity in your namespace:
 
      (use '[ring.velocity.core :only [render]])
	 
 Use `render` function to render template with vars:
 
      (render "test.vm" :name "dennis" :age 29)
	 
   The `test.vm` will be interpreted equals to:
   
      hello,dennis,your age is 29.
	 
 Use ring.velocity in compojure:
 
      (defroutes app-routes
         (GET "/" [] (render "test.vm" :name "dennis" :age 29))
         (route/not-found "Not Found"))
		 
 And if you just want to get a rendered template without variables,you can use `template-resources` to compile a route into compojure:
 
      (use '[ring.velocity.core :only [render template-resources]])
      (defroutes app-routes
         (GET "/" [] (render "test.vm" :name "dennis" :age 29))
	     (template-resources "/vm")
         (route/not-found "Not Found"))
		 
 Then you can access any velocity template files in `templates` folder by `http://host:port/vm/${template_file_name}`. It is just like the `resources` in compojure except it only serve velocity template requests.
	
 Use ring.velocity in ring:
 
      (use '[ring.util.response])
	  (response (render "test.vm" :name "dennis" :age 29))
   
 Custom velocity properties,just put a file named `ring-velocity.properties` to your classpath or resource paths.The default velocity properties is in [src/default/velocity.properties](https://github.com/killme2008/ring.velocity/blob/master/src/default/velocity.properties).
 
  A demo project with compojure is in [demo](https://github.com/killme2008/ring.velocity/tree/master/demo) folder.
 
## License

Copyright © 2012 dennis zhuang[killme2008@gmail.com]

Distributed under the Eclipse Public License, the same as Clojure.
