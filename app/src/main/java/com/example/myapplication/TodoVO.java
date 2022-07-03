package com.example.myapplication;

public class TodoVO {
    private boolean checked;
    private String content;


    public TodoVO(boolean checked, String content ) {
        this.checked = checked;
        this.content = content;
    }

    public boolean getChecked(){
        return this.checked;
    }

    public void setName(boolean checked){
        this.checked = checked;
    }

    public String getContent() {return this.content;}

    public void setContent(String content) {this.content = this.content;}


    @Override
    public String toString(){
        return "TodoVO{" +
                "checked='" + this.checked + '\'' +
                ", content='" + this.content + '\'' +
                '}';
    }

}
