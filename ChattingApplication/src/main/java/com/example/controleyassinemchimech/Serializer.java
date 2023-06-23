package com.example.controleyassinemchimech;

import java.io.*;
import java.util.HashSet;
import java.util.Set;


public class Serializer implements Serializable {
    File fichier;

    public Serializer(File fichier) {
        this.fichier = fichier;
    }
    void Write(Manager ms) throws IOException {
        Manager manager;
        ObjectInputStream entree=null;
        ObjectOutputStream sortie=null;
        boolean flag=false;
        File temp=new File("temp.txt");
        sortie=new ObjectOutputStream(new FileOutputStream(temp));
        try{
            entree=new ObjectInputStream(new FileInputStream(fichier));
            manager=(Manager) entree.readObject();
            while(manager!=null){
                if(ms.Id.equals(manager.Id)){
                    sortie.writeObject(ms);
                    flag=true;
                }
                else{
                    sortie.writeObject(manager);
                }
                manager=(Manager) entree.readObject();
            }

        }catch (FileNotFoundException e){}
        catch(ClassNotFoundException e){} catch (IOException e) {
            entree.close();
        }
        if(!flag) sortie.writeObject(ms);
        sortie.close();
        fichier.delete();
        temp.renameTo(fichier);
    }

    Set<Manager> read() throws IOException {
        Manager manager;
        Set<Manager> set=new HashSet<Manager>();
        ObjectInputStream entree=new ObjectInputStream(new FileInputStream(fichier));
        try{
            manager=(Manager) entree.readObject();
            while(manager!=null){
                set.add(manager);
                manager=(Manager) entree.readObject();
            }
        }catch(EOFException e){
            entree.close();
        }
        catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return set;

    }
}
