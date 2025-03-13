const button = document.getElementsByClassName("next")[0];
const card = document.getElementsByClassName("card")[0];
var is_bigger = false;

const stage1 = document.getElementById("stage1");
const stage2 = document.getElementById("stage2");


button.addEventListener("click", () => {
    if (is_bigger) {
        card.classList.remove("bigger");
        is_bigger = false;
      
            stage1.style.display = "block";
            stage2.style.display = "none";
      
        return;
    }
    else{
        card.classList.add("bigger");
        is_bigger = true;
        
            stage1.style.display = "none";
            stage2.style.display = "block";
        
        return;
    }

});