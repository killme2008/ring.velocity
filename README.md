# ring-velocity

A Clojure library designed to render velocity template for ring in clojure.

## Usage

 Adds dependency in leiningen project.clj:
     
      [ring.velocity "0.1.0-SNAPSHOT"]
	  
 Create a directory named `templates` in your project directory to keep all velocity templates.
 
 Create a template `templates/test.vm`:
   
      hello,$name,your age is $age.
	 
 Use ring.velocity in your namespace:
 
      (use '[ring.velocity :only [render]])
	 
 Use `render` function to render template with vars:
 
      (render "test.vm" :name "dennis" 29)
	 
   The `test.vm` will be interpreted equals to:
   
      hello,dennis,your age is 29.
	 
 Use ring.velocity in compojure:
 
      (defroutes app-routes
         (GET "/" [] (render "test.vm" :name "dennis" :age 29))
         (route/not-found "Not Found"))
	   
 Use ring.velocity in ring:
 
      (use '[ring.util.response])
	  (response (render "test.vm" :name "dennis" :age 29))
   
 Custom velocity properties,just put a file named `ring-velocity.properties` to your classpath or resource paths.The default velocity properties is in [src/default/velocity.properties](https://github.com/killme2008/ring.velocity/blob/master/src/default/velocity.properties).
 
## License

Copyright © 2012 FIXME

Distributed under the Eclipse Public License, the same as Clojure.
